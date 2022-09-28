package ru.rsreu.lab3;

import ru.rsreu.lab3.calculations.InfiniteSeriesSumCalculator;

import java.util.function.Function;

public class InfiniteSeriesSumRunner implements Runnable {

    private InfiniteSeriesSumCalculator calculator;
    private long lowerBound;
    private Function<Long, Double> predicate;
    private double infiniteSeriesSum;

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator,
                                   long lowerBound, Function<Long, Double> function) {
        this.calculator = calculator;
        this.lowerBound = lowerBound;
        this.predicate = function;
    }

    @Override
    public void run() {
        infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
    }

    public double getInfiniteSeriesSum() {
        return this.infiniteSeriesSum;
    }
}
