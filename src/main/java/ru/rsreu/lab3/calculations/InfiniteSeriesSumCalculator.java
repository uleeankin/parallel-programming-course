package ru.rsreu.lab3.calculations;

import ru.rsreu.lab3.predicates.InfinityConvergentSeriesPredicate;
import ru.rsreu.lab3.predicates.Predicate;

public class InfiniteSeriesSumCalculator {

    private final double inaccuracy;
    private final int LOGS_NUM = 20;

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
        long iterationsNumber = getIterationsNumber();

        do {
            functionResult = predicate.calculate(argument);
            result += functionResult;
            if (argument % (iterationsNumber / LOGS_NUM) == 0) {
                System.out.printf("Calculation progress: %.0f%%/100%%\n",
                                        getCalculationProgress(argument, iterationsNumber));
            }
            argument++;
        } while (Math.abs(functionResult) > this.inaccuracy);

        return result;
    }

    private long getIterationsNumber() {
        return Math.round(Math.sqrt(1 / (this.inaccuracy)) - 1);
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }


}
