package ru.rsreu.lab6;

import ru.rsreu.lab6.calculations.InfiniteSeriesSumRunner;
import ru.rsreu.lab6.logs.ProgressLogger;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CalculationsExecutor {

    private final List<InfiniteSeriesSumRunner> tasks;
    private final ExecutorService executor;
    private final ProgressLogger logger;

    public CalculationsExecutor(ExecutorService executor,
                                long tasksNumber, long inaccuracy) {
        this.executor = executor;
        this.tasks = new TaskService().getTasks(tasksNumber, inaccuracy);
        this.logger = new ProgressLogger(tasksNumber);
    }

    public double execute() throws InterruptedException {
        double result = 0;
        for (int i = 0; i < this.tasks.size(); i++) {
            Future<Double> future = executor.submit(this.tasks.get(i));
            try {
                result += future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new InterruptedException();
            }
            this.logger.getProgress(i + 1);
        }

        return result;
    }
}
