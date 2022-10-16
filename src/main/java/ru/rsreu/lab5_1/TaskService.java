package ru.rsreu.lab5_1;

import ru.rsreu.lab5_1.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab5_1.calculations.InfiniteSeriesSumRunner;
import ru.rsreu.lab5_1.logs.ProgressLogger;
import ru.rsreu.lab5_1.storage.SharedStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class TaskService {

    public TaskService() {

    }

    public List<InfiniteSeriesSumRunner> getTasks(long threadsNumber,
                                                  long totalCalculationsNumber,
                                                  SharedStorage sharedStorage) {
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
                    new InfiniteSeriesSumCalculator(inaccuracy, logger, sharedStorage),
                    lowerBound));
            lowerBound += calculationsNumberForOneTask;
        }

        return tasks;
    }
}
