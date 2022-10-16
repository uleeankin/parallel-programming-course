package ru.rsreu.lab5.storage;

import ru.rsreu.lab5.WhitespacePosition;

import java.util.ArrayList;
import java.util.List;

public class LazySharedStorage implements SharedStorage {

    private List<WhitespacePosition> sharedStorage;

    @Override
    public synchronized List<WhitespacePosition> getSharedStorage() {
        List<WhitespacePosition> localSharedStorage = this.sharedStorage;
        if (localSharedStorage == null) {
            synchronized (this) {
                localSharedStorage = this.sharedStorage;
                if (localSharedStorage == null) {
                    this.sharedStorage = localSharedStorage = new ArrayList<>();
                }
            }
        }
        return localSharedStorage;
    }

    @Override
    public synchronized void add(WhitespacePosition position) {
        this.getSharedStorage().add(position);
    }

    @Override
    public List<WhitespacePosition> getStorage() {
        return this.sharedStorage;
    }
}
