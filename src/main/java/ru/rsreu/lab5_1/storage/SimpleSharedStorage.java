package ru.rsreu.lab5_1.storage;

public class SimpleSharedStorage implements SharedStorage {

    private double calculationResultStorage = 0;

    @Override
    public synchronized void add(double value) {
        this.calculationResultStorage += value;
    }

    @Override
    public synchronized double get() {
        return this.calculationResultStorage;
    }
}
