package ru.rsreu.lab5;

import ru.rsreu.lab5.storage.LazySharedStorage;
import ru.rsreu.lab5.storage.SharedStorage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private final String fileName;
    private final SharedStorage sharedStorage;

    public Task(String fileName, SharedStorage sharedStorage) {
        this.fileName = fileName;
        this.sharedStorage = sharedStorage;
    }

    public void execute() throws InterruptedException {
        this.findFileWhitespacePositions(this.fileName);
    }

    private void findFileWhitespacePositions(String fileName)
            throws InterruptedException {

        List<String> fileData = getFileData(fileName);
        for (int i = 0; i < fileData.size(); i++) {
            for (int j = 0; j < fileData.get(i).length(); j++) {

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                if (fileData.get(i).charAt(j) == ' ') {
                    this.sharedStorage.add(
                            new WhitespacePosition(fileName, i, j));
                }

            }
        }
    }

    private List<String> getFileData(String fileName) {
        List<String> fileData = new ArrayList<>();

        try(FileReader fileReader = new FileReader(fileName)) {
           BufferedReader reader = new BufferedReader(fileReader);
           String line = null;
           while((line = reader.readLine()) != null) {
               fileData.add(line);
           }
           reader.close();
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", fileName);
        } catch (IOException e) {
            System.out.printf("Error reading in file %s%n", fileName);
        }

        return fileData;
    }
}
