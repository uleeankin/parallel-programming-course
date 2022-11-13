package ru.rsreu.queuing_system.repository;

import ru.rsreu.queuing_system.model.Product;
import ru.rsreu.queuing_system.model.Shop;
import ru.rsreu.queuing_system.model.ShopProduct;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;

public class ShopRepositoryImpl implements ShopRepository {

    private final Shop shop = new Shop();

    @Override
    public boolean addProduct(Product product, int amount, double price) {
        boolean operationResult;
        synchronized (this.shop) {
            operationResult = this.shop.getProducts()
                    .stream()
                    .noneMatch(
                            p -> p.getProduct().equals(product));
            if (operationResult) {
                this.shop.getProducts().add(new ShopProduct(product, amount, price));
            }
        }

        return operationResult;
    }

    @Override
    public double updateFundsAmount(double money) {
        synchronized (this.shop) {
            this.shop.setFundsAmount(this.shop.getFundsAmount() + money);
        }
        return this.shop.getFundsAmount();
    }

    @Override
    public List<ShopProduct> getAllProducts() {
        return this.shop.getProducts();
    }

    @Override
    public Optional<ShopProduct> getProduct(Product product) {
        return this.shop.getProducts()
                .stream()
                .filter(p -> p.getProduct().equals(product))
                .findFirst();
    }

    @Override
    public boolean updateProductAmount(Product product, int amount,
                                       ToIntBiFunction<Integer, Integer> func) {
        Optional<ShopProduct> foundProduct = this.getProduct(product);
        if (foundProduct.isPresent()) {
            synchronized (foundProduct.get()) {
                foundProduct.get().setAmount(
                        func.applyAsInt(
                                foundProduct.get().getAmount(), amount));
            }
        }
        return foundProduct.isPresent();
    }

    @Override
    public double getFundsAmount() {
        return this.shop.getFundsAmount();
    }
}
