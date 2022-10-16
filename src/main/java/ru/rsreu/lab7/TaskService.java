package ru.rsreu.lab7;

import ru.rsreu.lab7.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab7.calculations.InfiniteSeriesSumRunner;
import ru.rsreu.lab7.logs.ProgressLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class TaskService {

    public TaskService() {

    }

    public List<InfiniteSeriesSumRunner> getTasks(long threadsNumber,
                                                  long totalCalculationsNumber,
                                                  Semaphore semaphore,
                                                  CountDownLatch latch) {
        if (threadsNumber <= 0) {
            throw new IllegalArgumentException("Number of tasks must be positive");
        }

        List<InfiniteSeriesSumRunner> tasks = new ArrayList<>();
        long calculationsNumberForOneTask = totalCalculationsNumber / threadsNumber;
        long lowerBound = 1;

        ProgressLogger logger = new ProgressLogger(totalCalculationsNumber);

        for (long i = 1; i <= threadsNumber; i++) {
            long inaccuracy = i < threadsNumber ?
                    calculationsNumberForOneTask * i :
                    totalCalculationsNumber;

            tasks.add(new InfiniteSeriesSumRunner(
                    new InfiniteSeriesSumCalculator(inaccuracy, logger),
                    lowerBound, semaphore, latch));
            lowerBound += calculationsNumberForOneTask;
        }

        return tasks;
    }
}
