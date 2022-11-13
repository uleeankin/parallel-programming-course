package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.service.ClientService;
import ru.rsreu.queuing_system.service.ShopService;

import java.util.List;

public class ShopApiImpl implements ShopApi {

    ClientService clientService;
    ShopService shopService;

    public ShopApiImpl(ClientService clientService, ShopService shopService) {
        this.clientService = clientService;
        this.shopService = shopService;
    }

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
