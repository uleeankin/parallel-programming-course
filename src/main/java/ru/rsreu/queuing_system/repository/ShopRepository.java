package ru.rsreu.queuing_system.repository;

import ru.rsreu.queuing_system.model.base.Product;
import ru.rsreu.queuing_system.model.base.ShopProduct;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;

public interface ShopRepository {

    boolean addProduct(Product product, int amount, double price);

    double updateFundsAmount(double money);

    List<ShopProduct> getAllProducts();

    Optional<ShopProduct> getProduct(Product product);

    boolean updateProductAmount(Product product,
                                int amount,
                                ToIntBiFunction<Integer, Integer> func);

    double getFundsAmount();
}
