package ru.rsreu.queuing_system.service.shop;

import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;
import ru.rsreu.queuing_system.service.client.exception.ClientNotFoundException;
import ru.rsreu.queuing_system.service.shop.exception.ProductExistsException;
import ru.rsreu.queuing_system.service.shop.exception.ProductNotFoundException;

import java.util.List;

public class ShopImpl implements Shop {
    @Override
    public Client createClient(String name) {
        return null;
    }

    @Override
    public double getClientStatus(Client client)
            throws ClientNotFoundException {
        return 0;
    }

    @Override
    public void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException, ProductNotFoundException {

    }

    @Override
    public void addProduct(Product product, int count)
            throws ProductNotFoundException {

    }

    @Override
    public void addNewProduct(Product product, int count, double price)
            throws ProductExistsException {

    }

    @Override
    public List<ShopProduct> getAllProductsStatus() {
        return null;
    }
}
