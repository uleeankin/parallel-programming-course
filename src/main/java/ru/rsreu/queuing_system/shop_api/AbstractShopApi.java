package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.base.Client;
import ru.rsreu.queuing_system.model.base.Product;
import ru.rsreu.queuing_system.model.base.ShopProduct;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;

import java.util.Optional;

public abstract class AbstractShopApi implements ShopApi {

    protected final ClientRepository clientRepository;
    protected final ShopRepository shopRepository;

    public AbstractShopApi(ClientRepository clientRepository, ShopRepository shopRepository) {
        this.clientRepository = clientRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public Client createClient(String name) {
        Client newClient = new Client(name);
        this.clientRepository.addClient(newClient);
        return newClient;
    }

    @Override
    public double getClientStatus(Client client) throws ClientNotFoundException {
        Optional<Client> foundClient = clientRepository.getClient(client);
        if (foundClient.isPresent()) {
            return foundClient.get().getSpentMoneyAmount();
        }

        throw new ClientNotFoundException();
    }

    @Override
    public abstract void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException;

    protected Optional<Client> findClient(Client client)
            throws ClientNotFoundException {

        Optional<Client> foundClient = this.clientRepository.getClient(client);

        if (!foundClient.isPresent()) {
            throw new ClientNotFoundException();
        }
        return foundClient;
    }

    protected Optional<ShopProduct> findProduct(Product product)
            throws ProductNotFoundException {

        Optional<ShopProduct> foundProduct = this.shopRepository.getProduct(product);

        if (!foundProduct.isPresent()) {
            throw new ProductNotFoundException();
        }
        return foundProduct;
    }

    @Override
    public void addProduct(Product product, int amount) throws ProductNotFoundException {
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
