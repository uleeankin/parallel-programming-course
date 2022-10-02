package ru.rsreu.lab6;

import ru.rsreu.lab6.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab6.calculations.InfiniteSeriesSumRunner;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public TaskService() {

    }

    public List<InfiniteSeriesSumRunner> getTasks(long threadsNumber,
                                                        long totalCalculationsNumber) {
        if (threadsNumber <= 0) {
            throw new IllegalArgumentException("Number of tasks must be positive");
        }

        List<InfiniteSeriesSumRunner> tasks = new ArrayList<>();
        long calculationsNumberForOneTask = totalCalculationsNumber / threadsNumber;
        long lowerBound = 1;

        for (long i = 1; i <= threadsNumber; i++) {
            long inaccuracy = i < threadsNumber ?
                    calculationsNumberForOneTask * i :
                    totalCalculationsNumber;

            tasks.add(new InfiniteSeriesSumRunner(
                    new InfiniteSeriesSumCalculator(inaccuracy),
                    lowerBound));
            lowerBound += calculationsNumberForOneTask;
        }

        return tasks;
    }
}
