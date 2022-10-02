package ru.rsreu.lab6;

import ru.rsreu.lab6.calculations.InfiniteSeriesSumRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CalculationsExecutor {

    private final List<InfiniteSeriesSumRunner> tasks;
    private final ExecutorService executor;
    private final long progressOutputFrequency;

    public CalculationsExecutor(ExecutorService executor, int tasksNumber, long inaccuracy) {
        this.executor = executor;;
        this.tasks = new TaskService().getTasks(tasksNumber, inaccuracy);
        this.progressOutputFrequency = getOutputFrequency(inaccuracy);
    }

    public double execute() throws InterruptedException, ExecutionException {

        List<Future<Double>> futures = new ArrayList<>();
        for (InfiniteSeriesSumRunner task : this.tasks) {
            futures.add(executor.submit(task));
        }
        Thread progressChecker = startProgressCheck(executor, futures);

        double result = getCalculationResult(futures);

        executor.shutdown();
        progressChecker.join();
        return result;
    }

    private double getCalculationResult(List<Future<Double>> futures) throws ExecutionException, InterruptedException {
        double result = 0;
        for (Future<Double> future : futures) {
            result += future.get();
        }
        return result;
    }

    private Thread startProgressCheck(ExecutorService executor, List<Future<Double>> futures) {
        Thread progressChecker = new Thread(() -> {
            while(!executor.isShutdown()) {
                long finishedTasks = futures.stream().filter(Future::isDone).count();
                System.out.printf("Calculation progress: %.0f%%/100%%%n",
                                    finishedTasks * 100.0 / futures.size());
                try {
                    Thread.sleep(progressOutputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
        progressChecker.start();
        return progressChecker;
    }

    private long getOutputFrequency(long inaccuracy) {
        return Long.parseLong(Long.toString(inaccuracy).substring(0, 3));
    }
}
