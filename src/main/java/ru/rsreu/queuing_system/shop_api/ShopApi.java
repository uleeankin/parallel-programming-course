package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.model.base.Client;
import ru.rsreu.queuing_system.model.base.Product;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;

public interface ShopApi {

    Client createClient(String name);

    double getClientStatus(Client client) throws ClientNotFoundException;

    void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException;

    void addProduct(Product product, int amount)
            throws ProductNotFoundException;

    void createProduct(Product product, int amount, double price)
        throws ProductExistsException;

    int getShopStatus();
}
