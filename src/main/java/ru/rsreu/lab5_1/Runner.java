package ru.rsreu.lab5_1;

import ru.rsreu.lab5_1.storage.SharedStorage;
import ru.rsreu.lab5_1.storage.SimpleSharedStorage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Runner {

    private static final long INACCURACY = 30000000L;

    public static void main(String[] args) {

        int threadsNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        SharedStorage sharedStorage = new SimpleSharedStorage();
        try {
            long startTime = System.currentTimeMillis();

            double result = new CalculationsExecutor(executor,
                                    threadsNumber, INACCURACY, sharedStorage)
                                .execute();

            System.out.printf("Calculation time: %.3fs\n", ((System.currentTimeMillis() - startTime)/1000.0));
            terminateExecutorService(executor);
            System.out.println("Calculation result = " + result);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Execution was interrupted");
        } finally {
            shutDownExecutorService(executor);
        }
    }

    private static void terminateExecutorService(ExecutorService executor) throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void shutDownExecutorService(ExecutorService executor) {
        if (!executor.isTerminated()) {
            System.err.println("Canceling non-finished tasks!");
        }
        executor.shutdownNow();
        System.out.println("Shutdown was finished");
    }

}
