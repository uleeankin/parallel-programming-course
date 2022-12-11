package ru.rsreu.queuing_system.model.disruptor;

import ru.rsreu.queuing_system.model.base.Order;

public class OrderEvent {

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "order=" + order +
                '}';
    }
}
