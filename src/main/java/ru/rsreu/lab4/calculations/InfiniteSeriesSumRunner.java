package ru.rsreu.lab4.calculations;

import java.util.function.Function;

public class InfiniteSeriesSumRunner implements Runnable {

    private InfiniteSeriesSumCalculator calculator;
    private long lowerBound;
    private Function<Long, Double> predicate = (Long x) -> Math.sin(x) / x;
    private double infiniteSeriesSum;

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator,
                                   long lowerBound) {
        this.calculator = calculator;
        this.lowerBound = lowerBound;
    }

    @Override
    public void run() {
        infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
    }

    public double getInfiniteSeriesSum() {
        return this.infiniteSeriesSum;
    }
}
