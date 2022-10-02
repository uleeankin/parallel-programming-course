package ru.rsreu.lab6;

import java.util.concurrent.*;

public class Runner {

    private static final int THREADS_NUMBER = 10;
    private static final int TASKS_NUMBER = 100;
    private static final long INACCURACY = 50000000L;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        try {
            long startTime = System.currentTimeMillis();

            double result = new CalculationsExecutor(executor,
                                    TASKS_NUMBER, INACCURACY)
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