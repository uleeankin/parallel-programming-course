package ru.rsreu.queuing_system.model.base;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class Order {

    private final UUID orderId;
    private final Client client;
    private final Product product;
    private final int amount;
    private OrderStatus status;
    private final CountDownLatch latch = new CountDownLatch(1);

    public Order(Client client, Product product, int amount) {
        this.orderId = UUID.randomUUID();
        this.client = client;
        this.product = product;
        this.amount = amount;
        this.status = OrderStatus.REGISTERED;
    }

    public Client getClient() {
        return this.client;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getAmount() {
        return this.amount;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }
}
