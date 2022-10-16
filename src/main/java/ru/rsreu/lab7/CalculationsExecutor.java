package ru.rsreu.lab7;

import ru.rsreu.lab7.calculations.InfiniteSeriesSumRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculationsExecutor {

    private final List<InfiniteSeriesSumRunner> tasks;
    private final ExecutorService executor;


    public CalculationsExecutor(ExecutorService executor, int tasksNumber, long inaccuracy) {
        this.executor = executor;
        this.tasks = new TaskService().getTasks(tasksNumber, inaccuracy,
                            new Semaphore(tasksNumber / 2, true),
                            new CountDownLatch(tasksNumber));
    }

    public double execute() throws InterruptedException, ExecutionException {

        List<Future<Double>> futures = new ArrayList<>();
        for (InfiniteSeriesSumRunner task : this.tasks) {
            futures.add(executor.submit(task));
        }

        double result = getCalculationResult(futures);

        executor.shutdown();
        return result;
    }

    private double getCalculationResult(List<Future<Double>> futures) throws ExecutionException, InterruptedException {
        double result = 0;
        for (Future<Double> future : futures) {
            result += future.get();
        }
        return result;
    }
}
