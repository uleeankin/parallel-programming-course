package ru.rsreu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ProductType;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ClientRepositoryImpl;
import ru.rsreu.queuing_system.repository.ShopRepository;
import ru.rsreu.queuing_system.repository.ShopRepositoryImpl;
import ru.rsreu.queuing_system.shop_api.ShopApi;
import ru.rsreu.queuing_system.shop_api.ShopApiImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StressTest {

    ClientRepository clientRepo = new ClientRepositoryImpl();
    ShopRepository shopRepo = new ShopRepositoryImpl();
    ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

    @ParameterizedTest(name = "{index} - {0} stress test")
    @ValueSource(ints = {5, 100, 1000, 5000})
    public void stressShopApiTest(int clientsCount) throws ProductExistsException,
                                                                InterruptedException {

        double price = 100.0;
        int buyingProductAmount = 10;
        int purchasesNumber = 10;

        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsCount; i++) {
            Client newClient = this.shop.createClient("client" + i);
            clientRepo.updateFundsAmount(newClient, price * 1000, Double::sum);
            clients.add(newClient);
        }

        Product product = new Product("chocolate", ProductType.FOOD);
        int productAmount = buyingProductAmount * purchasesNumber * clientsCount;
        shop.createProduct(product, productAmount, price);

        List<Thread> threads = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(clientsCount);
        for (Client client : clients) {
            threads.add(new Thread(() -> {
                for (int i = 0; i < purchasesNumber; i++) {
                    try {
                        latch.countDown();
                        latch.await();
                        shop.buyProduct(client, product, buyingProductAmount);
                    } catch (ClientNotFoundException e) {
                        System.out.println("Client " + client.getName() + " not found");
                    } catch (ProductNotFoundException e) {
                        System.out.println("Product " + product.getName() + " not found");
                    } catch (InsufficientProductAmountException e) {
                        System.out.println("Not enough product " + product.getName());
                    } catch (InsufficientFundsAmountException e) {
                        System.out.println("Client " + client.getName() + " doesn't have enough money");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException();
                    }
                }
            }));
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        double shopBalance = shopRepo.getFundsAmount();
        double clientsBalance = 0;
        for (Client client : clients) {
            clientsBalance += client.getSpentMoneyAmount();
        }

        //double expectedSum = price * productAmount;
        Assertions.assertEquals(clientsBalance, shopBalance);
        //Assertions.assertEquals(expectedSum, shopBalance);
    }

    @RepeatedTest(50)
    public void repeatedStressTest() throws ProductExistsException,
                                                InterruptedException {
        this.stressShopApiTest(100);
    }

}
