package ru.rsreu;

import org.junit.Test;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.exception.shop.ProductExistsException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ProductType;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ClientRepositoryImpl;
import ru.rsreu.queuing_system.repository.ShopRepository;
import ru.rsreu.queuing_system.repository.ShopRepositoryImpl;
import ru.rsreu.queuing_system.shop_api.ShopApi;
import ru.rsreu.queuing_system.shop_api.ShopApiImpl;

public class ExceptionsTest {

    @Test(expected = ProductNotFoundException.class)
    public void testNonExistedProductsAdding()
            throws ProductNotFoundException {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);

        shop.addProduct(product, 10);
    }

    @Test(expected = ProductExistsException.class)
    public void testExistedProductsCreation()
            throws ProductExistsException, ProductNotFoundException {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        int count = 10;
        Product product = new Product("chocolate", ProductType.FOOD);
        shop.createProduct(product, count, 100.0);

        shop.addProduct(product, count);

        shop.createProduct(product, count * 2, 100.0);
    }

    @Test(expected = InsufficientProductAmountException.class)
    public void testInsufficientProductsAmount()
            throws ProductExistsException,
            InsufficientProductAmountException,
            ClientNotFoundException,
            ProductNotFoundException,
            InsufficientFundsAmountException {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        int buyingProductAmount = 10;
        shop.createProduct(product, buyingProductAmount / 2, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);
    }

    @Test(expected = InsufficientFundsAmountException.class)
    public void testInsufficientFundsAmount()
            throws ProductExistsException,
            InsufficientProductAmountException,
            ClientNotFoundException,
            ProductNotFoundException,
            InsufficientFundsAmountException {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        int buyingProductAmount = 10;
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount / 2, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);
    }
}
