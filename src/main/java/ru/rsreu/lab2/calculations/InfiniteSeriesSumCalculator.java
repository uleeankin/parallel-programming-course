package ru.rsreu.lab2.calculations;

import ru.rsreu.lab2.predicates.Predicate;

public class InfiniteSeriesSumCalculator {

    private final double inaccuracy;

    public InfiniteSeriesSumCalculator(double inaccuracy) {
        this.inaccuracy = inaccuracy;
    }

    public double calculate(long lowerBound, long upperBound, Predicate predicate) {

        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Upper bound must be more then lower one!");
        }

        if (lowerBound <= 0) {
            throw new IllegalArgumentException("Function argument must be natural number");
        }

        if (lowerBound == upperBound) {
            return predicate.calculate(lowerBound);
        }

        return calculateInfiniteSeriesSum(lowerBound, upperBound, predicate);
    }

    private double calculateInfiniteSeriesSum(long lowerBound, long upperBound, Predicate predicate) {
        double result = 0;
        long argument = lowerBound;
        double functionResult;

        do {
            functionResult = predicate.calculate(argument);
            result += functionResult;
            argument++;

        } while (Math.abs(functionResult) > this.inaccuracy
                && argument < upperBound);

        return result;
    }
}
