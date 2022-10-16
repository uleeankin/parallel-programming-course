package ru.rsreu.lab5;

import ru.rsreu.lab5.storage.LazySharedStorage;
import ru.rsreu.lab5.storage.SharedStorage;
import ru.rsreu.lab5.storage.SimpleSharedStorage;

import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private final SharedStorage sharedStorage = new LazySharedStorage();
    //private final SharedStorage sharedStorage = new SimpleSharedStorage();
    private List<Thread> tasks = new ArrayList<>();

    public FileReader() {

    }

    public List<WhitespacePosition> readFiles(List<String> files) {
        for (String file : files) {
            Thread taskThread = new Thread(
                    () -> {
                        try {
                            new Task(file, this.sharedStorage).execute();
                        } catch (InterruptedException e) {
                            System.out.printf("Execution in file %s was interrupted%n",
                                                file);
                        }
                    });
            taskThread.setName(file);
            tasks.add(taskThread);
        }

        for (Thread task : this.tasks) {
            task.start();
        }

        for (Thread task : this.tasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                task.interrupt();
            }
        }

        return this.sharedStorage.getStorage();
    }

}
