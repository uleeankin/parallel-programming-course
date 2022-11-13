package ru.rsreu.queuing_system.service;

import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.Shop;
import ru.rsreu.queuing_system.model.ShopProduct;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;

public class ShopServiceImpl implements ShopService {

    private final Shop shop = new Shop();


    @Override
    public ShopProduct addProduct(Product product, int amount, double price) {
        ShopProduct newProduct = new ShopProduct(product, amount, price);
        shop.getProducts().add(newProduct);
        return newProduct;
    }

    @Override
    public double updateFundsAmount(double money) {
        shop.setFundsAmount(shop.getFundsAmount() + money);
        return shop.getFundsAmount();
    }

    @Override
    public List<ShopProduct> getAllProducts() {
        return shop.getProducts();
    }

    @Override
    public Optional<ShopProduct> getProduct(Product product) {
        return shop.getProducts()
                .stream()
                .filter(p -> p.getProduct().equals(product))
                .findFirst();
    }

    @Override
    public ShopProduct updateProductAmount(Product product, int amount,
                                       ToIntBiFunction<Integer, Integer> func) {
        Optional<ShopProduct> foundProduct = this.getProduct(product);
        foundProduct.ifPresent(shopProduct -> shopProduct.setAmount(
                                shopProduct.getAmount() + amount));
        return foundProduct.get();
    }
}
