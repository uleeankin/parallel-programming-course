package ru.rsreu.queuing_system.model.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private final UUID id;
    private String name;
    private double fundsAmount = 0;
    private double spentMoneyAmount = 0;

    public Client(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFundsAmount() {
        return this.fundsAmount;
    }

    public void setFundsAmount(double fundsAmount) {
        this.fundsAmount = fundsAmount;
    }

    public double getSpentMoneyAmount() {
        return this.spentMoneyAmount;
    }

    public void setSpentMoneyAmount(double spentMoneyAmount) {
        this.spentMoneyAmount = spentMoneyAmount;
    }
}
