package ru.rsreu.lab5.storage;

import ru.rsreu.lab5.WhitespacePosition;

import java.util.List;

public interface SharedStorage {

    List<WhitespacePosition> getSharedStorage();
    void add(WhitespacePosition position);
    List<WhitespacePosition> getStorage();
}
