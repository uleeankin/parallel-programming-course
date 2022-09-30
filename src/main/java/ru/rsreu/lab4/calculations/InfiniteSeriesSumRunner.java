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

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator) {
        this.calculator = calculator;
        this.lowerBound = 1;
    }

    @Override
    public void run() {
        try {
            infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
        } catch (InterruptedException exception) {
            System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
        }

    }

    public double getInfiniteSeriesSum() {
        return this.infiniteSeriesSum;
    }
}
