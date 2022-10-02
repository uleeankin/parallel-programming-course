package ru.rsreu.lab6.calculations;

import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final long iterationsNumber;

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

    private double calculateInfiniteSeriesSum(long lowerBound, Function<Long, Double> predicate) {
        double result = 0;
        long argument;

        for (argument = lowerBound; argument <= this.iterationsNumber; argument++) {
            result += predicate.apply(argument);
        }

        return result;
    }

}
