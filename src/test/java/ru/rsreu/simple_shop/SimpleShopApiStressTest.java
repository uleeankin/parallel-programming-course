package ru.rsreu.simple_shop;

import ru.rsreu.base.StressTest;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ClientRepositoryImpl;
import ru.rsreu.queuing_system.repository.ShopRepository;
import ru.rsreu.queuing_system.repository.ShopRepositoryImpl;
import ru.rsreu.queuing_system.shop_api.ShopApi;
import ru.rsreu.queuing_system.shop_api.SimpleShopApiImpl;

public class SimpleShopApiStressTest extends StressTest {
    private static final ClientRepository CLIENT_REPOSITORY = new ClientRepositoryImpl();
    private static final ShopRepository SHOP_REPOSITORY = new ShopRepositoryImpl();
    private static final ShopApi SHOP_API = new SimpleShopApiImpl(CLIENT_REPOSITORY, SHOP_REPOSITORY);

    public SimpleShopApiStressTest() {
        super(CLIENT_REPOSITORY, SHOP_REPOSITORY, SHOP_API);
    }
}
