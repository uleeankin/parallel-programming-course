package ru.rsreu.lab6;

import ru.rsreu.lab6.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab6.calculations.InfiniteSeriesSumRunner;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public TaskService() {

    }

    public List<InfiniteSeriesSumRunner> getTasks(long tasksNumber, long totalCalculationsNumber) {
        if (tasksNumber <= 0) {
            throw new IllegalArgumentException("Number of tasks must be positive");
        }

        List<InfiniteSeriesSumRunner> tasks = new ArrayList<>();
        long calculationsNumberForOneTask = totalCalculationsNumber / tasksNumber;
        long lowerBound = 1;

        for (long i = 1; i <= tasksNumber; i++) {
            long inaccuracy = i < tasksNumber ?
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
