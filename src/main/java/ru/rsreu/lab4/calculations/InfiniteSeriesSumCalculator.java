package ru.rsreu.lab4.calculations;


import ru.rsreu.lab4.logs.ProgressLogger;

import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final long iterationsNumber;
    private final int LOGS_NUM = 20;

    public InfiniteSeriesSumCalculator(long inaccuracy) {
        this.iterationsNumber = inaccuracy;
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
        ProgressLogger logger = new ProgressLogger(this.iterationsNumber);

        for (argument = lowerBound; argument <= this.iterationsNumber; argument++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            logger.getProgress(argument);
            result += predicate.apply(argument);
        }

        return result;
    }

}
