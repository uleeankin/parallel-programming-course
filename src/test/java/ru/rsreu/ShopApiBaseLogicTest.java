package ru.rsreu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

public class ShopApiBaseLogicTest {

    @Test
    public void testClientCreation() {
        int clientsAmount = 100;

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        for (int i = 0; i < clientsAmount; i++) {
            shop.createClient("client" + i);
        }

        Assertions.assertEquals(clientsAmount, clientRepo.getAllClients().size());
    }

    @Test
    public void testCreationProducts()
            throws ProductExistsException {

        int productAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        shop.createProduct(product, productAmount, 100.0);

        Assertions.assertEquals(productAmount,
                shopRepo.getProduct(product).get().getAmount());
    }

    @Test
    public void testExistedProductsAdding()
            throws ProductExistsException, ProductNotFoundException {

        int productAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        int count = 10;
        Product product = new Product("chocolate", ProductType.FOOD);
        shop.createProduct(product, count, 100.0);

        shop.addProduct(product, productAmount);

        Assertions.assertEquals(count + productAmount,
                shopRepo.getProduct(product).get().getAmount());
    }

    @Test
    public void testClientStatus()
            throws ProductExistsException,
            InsufficientProductAmountException,
            ClientNotFoundException,
            ProductNotFoundException,
            InsufficientFundsAmountException {

        int buyingProductAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);

        Assertions.assertEquals(price * buyingProductAmount,
                shop.getClientStatus(client));
    }

    @Test
    public void testShopStatus()
            throws ProductExistsException,
            InsufficientProductAmountException,
            ClientNotFoundException,
            ProductNotFoundException,
            InsufficientFundsAmountException {

        int buyingProductAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);

        Assertions.assertEquals(0, shop.getShopStatus());
    }

    @Test
    public void testBalance()
            throws ProductExistsException,
            InsufficientProductAmountException,
            ClientNotFoundException,
            ProductNotFoundException,
            InsufficientFundsAmountException {

        int buyingProductAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount / 2);

        Assertions.assertEquals(price * buyingProductAmount,
                clientRepo.getClient(client).get().getSpentMoneyAmount()
                    + shopRepo.getFundsAmount());
    }
}
