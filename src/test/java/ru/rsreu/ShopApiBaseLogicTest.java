package ru.rsreu;

import org.junit.jupiter.api.Assertions;
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

    @ParameterizedTest(name = "{index} - {0} client creation test")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    public void testClientCreation(int clientsAmount) {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        for (int i = 0; i < clientsAmount; i++) {
            shop.createClient("client" + i);
        }

        Assertions.assertEquals(clientsAmount, clientRepo.getAllClients().size());
    }

    @ParameterizedTest(name = "{index} - {0} creation product test")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    public void testCreationProducts(int productAmount)
            throws ProductExistsException {

        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        ShopApi shop = new ShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        shop.createProduct(product, productAmount, 100.0);

        Assertions.assertEquals(productAmount,
                shopRepo.getProduct(product).get().getAmount());
    }

    @ParameterizedTest(name = "{index} - {0} adding product test")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    public void testExistedProductsAdding(int productAmount)
            throws ProductExistsException, ProductNotFoundException {

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

    @ParameterizedTest(name = "{index} - {0} client status test")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    public void testClientStatus(int buyingProductAmount)
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
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);

        Assertions.assertEquals(price * buyingProductAmount,
                shop.getClientStatus(client));
    }

    @ParameterizedTest(name = "{index} - {0} shop status test")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    public void testShopStatus(int buyingProductAmount)
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
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount);

        Assertions.assertEquals(0, shop.getShopStatus());
    }

    @ParameterizedTest(name = "{index} - {0} balance test")
    @ValueSource(ints = {100, 200, 300, 400, 500})
    public void testBalance(int buyingProductAmount)
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
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount / 2);

        Assertions.assertEquals(price * buyingProductAmount,
                clientRepo.getClient(client).get().getSpentMoneyAmount()
                    + shopRepo.getFundsAmount());
    }
}
