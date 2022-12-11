package ru.rsreu.disruptor_shop;

import ru.rsreu.base.StressTest;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ClientRepositoryImpl;
import ru.rsreu.queuing_system.repository.ShopRepository;
import ru.rsreu.queuing_system.repository.ShopRepositoryImpl;
import ru.rsreu.queuing_system.shop_api.DisruptorShopApiImpl;
import ru.rsreu.queuing_system.shop_api.ShopApi;

public class DisruptorShopApiStressTest extends StressTest {

    private static final ClientRepository CLIENT_REPOSITORY = new ClientRepositoryImpl();
    private static final ShopRepository SHOP_REPOSITORY = new ShopRepositoryImpl();
    private static final ShopApi SHOP_API = new DisruptorShopApiImpl(CLIENT_REPOSITORY, SHOP_REPOSITORY);

    public DisruptorShopApiStressTest() {
        super(CLIENT_REPOSITORY, SHOP_REPOSITORY, SHOP_API);
    }
}
