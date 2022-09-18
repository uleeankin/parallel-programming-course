package ru.rsreu.lab3;

import ru.rsreu.lab3.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab3.predicates.InfinityConvergentSeriesPredicate;

public class InfiniteSeriesSumRunner implements Runnable {

    private InfiniteSeriesSumCalculator calculator;
    private long lowerBound;
    private InfinityConvergentSeriesPredicate predicate;
    private double infiniteSeriesSum;

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator,
                                   long lowerBound,
                                   InfinityConvergentSeriesPredicate predicate) {
        this.calculator = calculator;
        this.lowerBound = lowerBound;
        this.predicate = predicate;
    }

    @Override
    public void run() {
        infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
    }

    public double getInfiniteSeriesSum() {
        return this.infiniteSeriesSum;
    }
}
