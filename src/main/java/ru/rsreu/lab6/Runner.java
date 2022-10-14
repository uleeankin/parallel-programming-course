package ru.rsreu.lab6;

import java.util.concurrent.*;

public class Runner {

    private static int tasksNumber = 10;
    private static final long INACCURACY = 100000000L;

    public static void main(String[] args) {

        int threadsNumber = Runtime.getRuntime().availableProcessors() * 2;
        tasksNumber = threadsNumber;
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        try {
            long startTime = System.currentTimeMillis();

            double result = new CalculationsExecutor(executor,
                                    tasksNumber, INACCURACY)
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
