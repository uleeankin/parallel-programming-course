package ru.rsreu.queuing_system.service;

import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.ShopProduct;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;

public interface ShopService {

    ShopProduct addProduct(Product product, int amount, double price);

    double updateFundsAmount(double money);

    List<ShopProduct> getAllProducts();

    Optional<ShopProduct> getProduct(Product product);

    ShopProduct updateProductAmount(Product product,
                                int amount,
                                ToIntBiFunction<Integer, Integer> func);



}
