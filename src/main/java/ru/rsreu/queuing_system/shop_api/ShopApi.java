package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;

import java.util.List;

public interface ShopApi {

    Client createClient(String name);

    double getClientStatus(Client client) throws ClientNotFoundException;

    void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException;

    void addProduct(Product product, int count)
            throws ProductNotFoundException;

    void createProduct(Product product, int count, double price)
        throws ProductExistsException;

    int getShopStatus();
}
