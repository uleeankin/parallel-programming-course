package ru.rsreu.queuing_system.shop_api;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.base.*;
import ru.rsreu.queuing_system.model.disruptor.OrderEvent;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;

import java.util.Optional;

public class DisruptorShopApiImpl extends AbstractShopApi
                                    implements EventHandler<OrderEvent> {

    private static final int BUFFER_SIZE = (int) Math.pow(2, 14);

    private final Disruptor<OrderEvent> disruptor;

    public DisruptorShopApiImpl(ClientRepository clientRepository, ShopRepository shopRepository) {
        super(clientRepository, shopRepository);
        disruptor = new Disruptor<>(OrderEvent::new, BUFFER_SIZE, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(this);
        disruptor.start();
    }

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        completeOrder(orderEvent.getOrder());
    }

    @Override
    public void buyProduct(Client client, Product product, int amount) {
        try {
            Order order = new Order(client, product, amount);
            this.disruptor.getRingBuffer().publishEvent((event, sequence) -> event.setOrder(order));
            order.await();
        } catch (InterruptedException e) {
            System.err.println("Shop work was stopped");
        }
    }

    private void completeOrder(Order order)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException {

        Optional<Client> foundClient = this.findClient(order.getClient());
        Optional<ShopProduct> foundProduct = this.findProduct(order.getProduct());

        declineOrder(foundClient.get(), foundProduct.get(), order);

        double totalCost = order.getAmount() * foundProduct.get().getPrice();

        this.shopRepository.updateProductAmount(foundProduct.get().getProduct(), order.getAmount(),
                (oldAmount, reservedAmount) -> oldAmount - reservedAmount);
        this.shopRepository.updateFundsAmount(totalCost);

        this.clientRepository.updateFundsAmount(foundClient.get(), totalCost,
                (fundsAmount, cost) -> fundsAmount - cost);
        this.clientRepository.updateSpentMoney(foundClient.get(), totalCost, Double::sum);

        order.setStatus(OrderStatus.DONE);
    }

    private void declineOrder(Client client, ShopProduct product, Order order)
            throws InsufficientProductAmountException,
            InsufficientFundsAmountException {
        double totalCost;

        if (product.getAmount() < order.getAmount()) {
            order.setStatus(OrderStatus.ERROR);
            throw new InsufficientProductAmountException();
        }

        totalCost = order.getAmount() * product.getPrice();

        if (client.getFundsAmount() < totalCost) {
            order.setStatus(OrderStatus.ERROR);
            throw new InsufficientFundsAmountException();
        }
    }
}
