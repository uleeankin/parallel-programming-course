package ru.rsreu.queuing_system;

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
import ru.rsreu.queuing_system.shop_api.QueueShopApiImpl;
import ru.rsreu.queuing_system.shop_api.ShopApi;

public class ApplicationRunner {
    public static void main(String[] args) throws ProductExistsException {
        int buyingProductAmount = 100;
        ClientRepository clientRepo = new ClientRepositoryImpl();
        ShopRepository shopRepo = new ShopRepositoryImpl();
        QueueShopApiImpl shop = new QueueShopApiImpl(clientRepo, shopRepo);

        Product product = new Product("chocolate", ProductType.FOOD);
        double price = 100.0;
        shop.createProduct(product, buyingProductAmount, price);

        Client client = shop.createClient("client");
        clientRepo.updateFundsAmount(client, price * buyingProductAmount, Double::sum);

        shop.buyProduct(client, product, buyingProductAmount / 2);
        //Thread.sleep(10);
        System.out.println(shop.getShopStatus());
    }
}
