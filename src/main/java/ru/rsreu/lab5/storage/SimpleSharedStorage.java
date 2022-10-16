package ru.rsreu.lab5.storage;

import ru.rsreu.lab5.WhitespacePosition;

import java.util.ArrayList;
import java.util.List;

public class SimpleSharedStorage implements SharedStorage {

    private List<WhitespacePosition> sharedStorage;

    public SimpleSharedStorage() {
        this.sharedStorage = new ArrayList<>();
    }

    @Override
    public synchronized List<WhitespacePosition> getSharedStorage() {
        return this.sharedStorage;
    }

    @Override
    public synchronized void add(WhitespacePosition position) {
        this.sharedStorage.add(position);
    }

    @Override
    public List<WhitespacePosition> getStorage() {
        return this.sharedStorage;
    }
}
