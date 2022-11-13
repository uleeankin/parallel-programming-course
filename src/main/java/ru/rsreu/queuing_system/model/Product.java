package ru.rsreu.queuing_system.model;

public class Product {

    private String name;
    private final ProductType type;

    public Product(String name, ProductType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }
}
