package ru.rsreu;

import ru.rsreu.lab7.calculations.InfiniteSeriesSumCalculator;

import java.util.function.Function;

public class TestTaskRunner implements Runnable {

    private final InfiniteSeriesSumCalculator calculator;
    private final Function<Long, Double> predicate = (Long x) -> Math.sin(x) / x;
    private double infiniteSeriesSum;

    public TestTaskRunner(InfiniteSeriesSumCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void run() {
        infiniteSeriesSum = calculator.calculate(1, predicate);
    }

    public double getInfiniteSeriesSum() {
        return this.infiniteSeriesSum;
    }

}
