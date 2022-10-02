package ru.rsreu.lab6.calculations;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class InfiniteSeriesSumRunner implements Callable<Double> {

    private final InfiniteSeriesSumCalculator calculator;
    private final long lowerBound;
    private final Function<Long, Double> predicate = (Long x) -> Math.sin(x) / x;
    private double infiniteSeriesSum = 0;

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator,
                                   long lowerBound) {
        this.calculator = calculator;
        this.lowerBound = lowerBound;
    }

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator) {
        this.calculator = calculator;
        this.lowerBound = 1;
    }

    @Override
    public Double call() {
        try {
            this.infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
        } catch (InterruptedException exception) {
            System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
        }

        return this.infiniteSeriesSum;
    }
}
