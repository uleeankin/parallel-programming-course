package ru.rsreu.lab2.calculations;

import ru.rsreu.lab2.predicates.Predicate;

public class InfiniteSeriesSumCalculator {

    private final double inaccuracy;

    public InfiniteSeriesSumCalculator(double inaccuracy) {
        this.inaccuracy = inaccuracy;
    }

    public double calculate(long lowerBound, Predicate predicate) {

        if (lowerBound <= 0) {
            throw new IllegalArgumentException("Function argument must be natural number");
        }

        return calculateInfiniteSeriesSum(lowerBound, predicate);
    }

    private double calculateInfiniteSeriesSum(long lowerBound, Predicate predicate) {
        double result = 0;
        long argument = lowerBound;
        double functionResult;

        do {
            functionResult = predicate.calculate(argument);
            result += functionResult;
            argument++;
        } while (Math.abs(functionResult) > this.inaccuracy);

        return result;
    }
}
