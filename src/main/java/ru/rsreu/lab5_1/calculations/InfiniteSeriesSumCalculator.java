package ru.rsreu.lab5_1.calculations;

import ru.rsreu.lab5_1.logs.ProgressLogger;
import ru.rsreu.lab5_1.storage.SharedStorage;

import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final long iterationsNumber;
    private final ProgressLogger logger;
    private final SharedStorage sharedStorage;

    public InfiniteSeriesSumCalculator(long inaccuracy,
                                       ProgressLogger logger,
                                       SharedStorage sharedStorage) {
        this.iterationsNumber = inaccuracy;
        this.logger = logger;
        this.sharedStorage = sharedStorage;
    }

    public double calculate(long lowerBound, Function<Long, Double> predicate)
            throws InterruptedException{
        if (lowerBound <= 0) {
            throw new IllegalArgumentException("Function argument must be natural number");
        }

        return calculateInfiniteSeriesSum(lowerBound, predicate);
    }

    private double calculateInfiniteSeriesSum(long lowerBound, Function<Long, Double> predicate)
            throws InterruptedException {
        double result = 0;
        long argument;
        for (argument = lowerBound; argument <= this.iterationsNumber; argument++) {

            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }

            this.logger.getProgress(1);

            double functionResult = predicate.apply(argument);
            this.sharedStorage.add(functionResult);
            result += functionResult;
        }
        //this.logger.getProgress(this.iterationsNumber - lowerBound);
        return result;
    }

}
