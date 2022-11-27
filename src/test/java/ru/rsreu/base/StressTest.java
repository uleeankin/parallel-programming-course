package ru.rsreu.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ProductType;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;
import ru.rsreu.queuing_system.shop_api.ShopApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class StressTest {

    private static final double PRICE = 100.0;
    private static final int BUYING_PRODUCT_AMOUNT = 100;
    private static final int CLIENTS_COUNT = 10000;
    private static final int PURCHASES_NUMBER = 10;

    private final ClientRepository clientRepo;
    private final ShopRepository shopRepo;
    private final ShopApi shop;

    public StressTest(ClientRepository clientRepo, ShopRepository shopRepo, ShopApi shop) {
        this.clientRepo = clientRepo;
        this.shopRepo = shopRepo;
        this.shop = shop;
    }

    @Test
    public void stressShopApiTest() throws ProductExistsException,
                                                InterruptedException {

        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < CLIENTS_COUNT; i++) {
            Client newClient = shop.createClient("client" + i);
            clientRepo.updateFundsAmount(newClient, PRICE * BUYING_PRODUCT_AMOUNT * PURCHASES_NUMBER, Double::sum);
            clients.add(newClient);
        }

        Product product = new Product("chocolate", ProductType.FOOD);
        int productAmount = BUYING_PRODUCT_AMOUNT * PURCHASES_NUMBER * CLIENTS_COUNT;
        shop.createProduct(product, productAmount, PRICE);

        List<Thread> threads = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(CLIENTS_COUNT);
        for (Client client : clients) {
            threads.add(new Thread(() -> {
                try {
                    latch.countDown();
                    latch.await();
                    for (int i = 0; i < PURCHASES_NUMBER; i++) {
                        shop.buyProduct(client, product, BUYING_PRODUCT_AMOUNT);
                    }
                } catch (InsufficientProductAmountException e) {
                    System.out.println("Not enough product " + product.getName());
                } catch (InsufficientFundsAmountException e) {
                    System.out.println("Client " + client.getName() + " doesn't have enough money");
                }  catch (ClientNotFoundException | ProductNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException();
                }
            }));
        }

        long startTime = System.currentTimeMillis();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.printf("%s processes %.2f purchases per second\n",
                shop.getClass().getName(),
                (CLIENTS_COUNT * PURCHASES_NUMBER)
                        / ((System.currentTimeMillis() - startTime) / 1000.0));

        double shopBalance = shopRepo.getFundsAmount();
        double clientsBalance = 0;
        for (Client client : clients) {
            try {
                clientsBalance += shop.getClientStatus(client);
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
        }

        Assertions.assertEquals(clientsBalance, shopBalance);
    }
}
