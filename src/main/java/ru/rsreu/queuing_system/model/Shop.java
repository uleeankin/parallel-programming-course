package ru.rsreu.queuing_system.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<ShopProduct> products = new ArrayList<>();
    private double fundsAmount = 0;

    public Shop(List<ShopProduct> products, double fundsAmount) {
        this.products = products;
        this.fundsAmount = fundsAmount;
    }

    public Shop(double fundsAmount) {
        this.fundsAmount = fundsAmount;
    }

    public Shop() {

    }

    public List<ShopProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ShopProduct> products) {
        this.products = products;
    }

    public double getFundsAmount() {
        return fundsAmount;
    }

    public void setFundsAmount(double fundsAmount) {
        this.fundsAmount = fundsAmount;
    }
}
