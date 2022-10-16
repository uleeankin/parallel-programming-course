package ru.rsreu.lab5_1;

import ru.rsreu.lab5_1.calculations.InfiniteSeriesSumRunner;
import ru.rsreu.lab5_1.storage.SharedStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculationsExecutor {

    private final List<InfiniteSeriesSumRunner> tasks;
    private final ExecutorService executor;
    private SharedStorage sharedStorage;


    public CalculationsExecutor(ExecutorService executor, int tasksNumber,
                                long inaccuracy, SharedStorage sharedStorage) {
        this.executor = executor;
        this.tasks = new TaskService().getTasks(tasksNumber, inaccuracy, sharedStorage);
        this.sharedStorage = sharedStorage;
    }

    public double execute() throws InterruptedException, ExecutionException {

        List<Future<Double>> futures = new ArrayList<>();
        for (InfiniteSeriesSumRunner task : this.tasks) {
            futures.add(executor.submit(task));
        }

        waitFutureExecution(futures);

        executor.shutdown();

        return this.sharedStorage.get();
    }

    private void waitFutureExecution(List<Future<Double>> futures)
            throws ExecutionException, InterruptedException {
        for (Future<Double> future : futures) {
            future.get();
        }
    }
}
