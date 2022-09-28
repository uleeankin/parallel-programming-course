package ru.rsreu.lab3.calculations;


import java.util.function.Function;

public class InfiniteSeriesSumCalculator {

    private final long inaccuracy;
    private final int LOGS_NUM = 20;

    public InfiniteSeriesSumCalculator(long inaccuracy) {
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
        int logsFrequency = getLogsFrequency();

        for (argument = lowerBound; argument <= this.inaccuracy; argument++) {
            result += predicate.apply(argument);
            if (argument % logsFrequency == 0) {
                System.out.printf("Calculation progress: %.0f%%/100%%\n",
                        getCalculationProgress(argument, this.inaccuracy));
            }
        }

        return result;
    }

    private int getLogsFrequency() {
        return (int) this.inaccuracy / this.LOGS_NUM;
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }


}
