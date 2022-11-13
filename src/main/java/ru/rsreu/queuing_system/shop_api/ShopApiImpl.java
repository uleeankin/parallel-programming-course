package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;

import java.util.Optional;

public class ShopApiImpl implements ShopApi {

    ClientRepository clientRepository;
    ShopRepository shopRepository;

    public ShopApiImpl(ClientRepository clientRepository, ShopRepository shopRepository) {
        this.clientRepository = clientRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public Client createClient(String name)
    {
        Client newClient = new Client(name);
        this.clientRepository.addClient(newClient);
        return newClient;
    }

    @Override
    public double getClientStatus(Client client)
            throws ClientNotFoundException {

        Optional<Client> foundClient = clientRepository.getClient(client);
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

        Optional<Client> foundClient = this.findClient(client);
        Optional<ShopProduct> foundProduct = this.findProduct(product);
        double totalCost;

        if (foundProduct.get().getAmount() < amount) {
            throw new InsufficientProductAmountException();
        }

        totalCost = amount * foundProduct.get().getPrice();

        if (foundClient.get().getFundsAmount() < totalCost) {
            throw new InsufficientFundsAmountException();
        }

        this.shopRepository.updateProductAmount(foundProduct.get().getProduct(), amount,
                (oldAmount, reservedAmount) -> oldAmount - reservedAmount);

        this.clientRepository.updateFundsAmount(foundClient.get(), totalCost,
                (fundsAmount, cost) -> fundsAmount - cost);
        this.clientRepository.updateSpentMoney(foundClient.get(), totalCost, Double::sum);

        this.shopRepository.updateFundsAmount(totalCost);


    }

    private Optional<Client> findClient(Client client)
        throws ClientNotFoundException {

        Optional<Client> foundClient = this.clientRepository.getClient(client);

        if (!foundClient.isPresent()) {
            throw new ClientNotFoundException();
        }
        return foundClient;
    }

    private Optional<ShopProduct> findProduct(Product product)
            throws ProductNotFoundException {

        Optional<ShopProduct> foundProduct = this.shopRepository.getProduct(product);

        if (!foundProduct.isPresent()) {
            throw new ProductNotFoundException();
        }
        return foundProduct;
    }

    @Override
    public void addProduct(Product product, int amount)
            throws ProductNotFoundException {

        if (!this.shopRepository.updateProductAmount(product, amount, Integer::sum)) {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public void createProduct(Product product, int amount, double price)
            throws ProductExistsException {

        if (!this.shopRepository.addProduct(product, amount, price)) {
            throw new ProductExistsException();
        }
    }

    @Override
    public int getShopStatus() {
        int amount = 0;
        for (ShopProduct product : this.shopRepository.getAllProducts()) {
            amount += product.getAmount();
        }
        return amount;
    }
}
