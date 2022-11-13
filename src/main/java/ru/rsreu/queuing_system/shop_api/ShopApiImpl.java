package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.service.ClientService;
import ru.rsreu.queuing_system.service.ShopService;

import java.util.List;
import java.util.Optional;

public class ShopApiImpl implements ShopApi {

    ClientService clientService;
    ShopService shopService;

    public ShopApiImpl(ClientService clientService, ShopService shopService) {
        this.clientService = clientService;
        this.shopService = shopService;
    }

    @Override
    public Client createClient(String name)
    {
        Client newClient = new Client(name);
        this.clientService.addClient(newClient);
        return newClient;
    }

    @Override
    public double getClientStatus(Client client)
            throws ClientNotFoundException {

        Optional<Client> foundClient = clientService.getClient(client);
        if (foundClient.isPresent()) {
            return foundClient.get().getSpentMoneyAmount();
        }

        throw new ClientNotFoundException();
    }

    @Override
    public void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException {

        Client foundClient = this.findClient(client);
        ShopProduct foundProduct = this.findProduct(product);
        double totalCost;

        if (foundProduct.getAmount() < amount) {
            throw new InsufficientProductAmountException();
        }

        totalCost = amount * foundProduct.getPrice();

        if (foundClient.getFundsAmount() < totalCost) {
            throw new InsufficientFundsAmountException();
        }

        this.shopService.updateProductAmount(foundProduct.getProduct(), amount,
                (oldAmount, reservedAmount) -> oldAmount - reservedAmount);

        this.clientService.updateFundsAmount(foundClient, totalCost,
                (fundsAmount, cost) -> fundsAmount - cost);
        this.clientService.updateSpentMoney(foundClient, totalCost, Double::sum);
        
        this.shopService.updateFundsAmount(totalCost);

    }

    private Client findClient(Client client)
        throws ClientNotFoundException {

        Optional<Client> foundClient = this.clientService.getClient(client);

        if (!foundClient.isPresent()) {
            throw new ClientNotFoundException();
        }
        return foundClient.get();
    }

    private ShopProduct findProduct(Product product)
            throws ProductNotFoundException {

        Optional<ShopProduct> foundProduct = this.shopService.getProduct(product);

        if (!foundProduct.isPresent()) {
            throw new ProductNotFoundException();
        }
        return foundProduct.get();
    }

    @Override
    public void addProduct(Product product, int amount)
            throws ProductNotFoundException {

        if (!this.shopService.updateProductAmount(product, amount, Integer::sum)) {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public void createProduct(Product product, int amount, double price)
            throws ProductExistsException {

        if (!this.shopService.addProduct(product, amount, price)) {
            throw new ProductExistsException();
        }
    }

    @Override
    public List<ShopProduct> getAllProductsStatus() {
        return this.shopService.getAllProducts();
    }
}
