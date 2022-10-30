package ru.rsreu.lab7.calculations;

import ru.rsreu.lab7.logs.ProgressLogger;

import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final long iterationsNumber;
    private ProgressLogger logger = new ProgressLogger(0);

    public InfiniteSeriesSumCalculator(long inaccuracy, ProgressLogger logger) {
        this.iterationsNumber = inaccuracy;
        this.logger = logger;
    }

    public InfiniteSeriesSumCalculator(long inaccuracy) {
        this.iterationsNumber = inaccuracy;
    }

    public double calculate(long lowerBound, Function<Long, Double> predicate) {
        if (lowerBound <= 0) {
            throw new IllegalArgumentException("Function argument must be natural number");
        }

        return calculateInfiniteSeriesSum(lowerBound, predicate);
    }

    private double calculateInfiniteSeriesSum(long lowerBound, Function<Long, Double> predicate){
        double result = 0;
        long argument;

        for (argument = lowerBound; argument <= this.iterationsNumber; argument++) {
            result += predicate.apply(argument);
            //this.logger.getProgress(1);
        }

        //this.logger.getProgress(this.iterationsNumber - lowerBound);
        return result;
    }

}
