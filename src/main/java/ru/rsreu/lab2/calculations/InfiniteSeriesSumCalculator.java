package ru.rsreu.lab2.calculations;


import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final double inaccuracy;

    public InfiniteSeriesSumCalculator(double inaccuracy) {
        this.inaccuracy = inaccuracy;
    }

    public double calculate(long lowerBound, Function<Long, Double> predicate) {

        if (lowerBound <= 0) {
            throw new IllegalArgumentException("Function argument must be natural number");
        }

        return calculateInfiniteSeriesSum(lowerBound, predicate);
    }

    private double calculateInfiniteSeriesSum(long lowerBound, Function<Long, Double> predicate) {
        double result = 0;
        long argument;

        for (argument = lowerBound; argument <= this.inaccuracy; argument++) {
            result += predicate.apply(argument);
        }

        return result;
    }
}
