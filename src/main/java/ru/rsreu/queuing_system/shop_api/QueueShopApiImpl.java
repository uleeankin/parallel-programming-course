package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.*;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueShopApiImpl extends AbstractShopApi {

    private final BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    public QueueShopApiImpl(ClientRepository clientRepository, ShopRepository shopRepository) {
        super(clientRepository, shopRepository);
        this.getShopThread().start();
    }

    @Override
    public void buyProduct(Client client, Product product, int amount) {
        try {
            Order order = new Order(client, product, amount);
            this.orders.put(order);
            order.await();
        } catch (InterruptedException e) {
            System.err.println("Shop work was stopped");
        }
    }

    private Thread getShopThread() {
        Thread thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                Order order = null;
                try {
                    order = orders.take();
                    completeOrder(order);
                    order.setStatus(OrderStatus.DONE);
                } catch (InterruptedException  e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Shop work was stopped");
                } catch (ClientNotFoundException
                        | ProductNotFoundException
                        | InsufficientProductAmountException
                        | InsufficientFundsAmountException e) {
                    System.out.println("Order " + order.getOrderId() + " was declined");
                }
            }
        });
        thread.setDaemon(true);
        return thread;
    }

    private void completeOrder(Order order)
            throws ClientNotFoundException,
            ProductNotFoundException, InsufficientProductAmountException, InsufficientFundsAmountException {
        Optional<Client> foundClient = this.findClient(order.getClient());
        Optional<ShopProduct> foundProduct = this.findProduct(order.getProduct());
        double totalCost;

        if (foundProduct.get().getAmount() < order.getAmount()) {
            order.setStatus(OrderStatus.ERROR);
            throw new InsufficientProductAmountException();
        }

        totalCost = order.getAmount() * foundProduct.get().getPrice();

        if (foundClient.get().getFundsAmount() < totalCost) {
            order.setStatus(OrderStatus.ERROR);
            throw new InsufficientFundsAmountException();
        }

        this.shopRepository.updateProductAmount(foundProduct.get().getProduct(), order.getAmount(),
                (oldAmount, reservedAmount) -> oldAmount - reservedAmount);
        this.shopRepository.updateFundsAmount(totalCost);

        this.clientRepository.updateFundsAmount(foundClient.get(), totalCost,
                (fundsAmount, cost) -> fundsAmount - cost);
        this.clientRepository.updateSpentMoney(foundClient.get(), totalCost, Double::sum);
    }
}
