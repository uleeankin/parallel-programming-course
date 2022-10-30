package ru.rsreu.lab7.calculations;

import ru.rsreu.lab7.realisation.Semaphore;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class InfiniteSeriesSumRunner implements Callable<Double> {

    private final InfiniteSeriesSumCalculator calculator;
    private final long lowerBound;
    private final Function<Long, Double> predicate = (Long x) -> Math.sin(x) / x;
    private double infiniteSeriesSum = 0;
    private Semaphore semaphore;
    private CountDownLatch latch;

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator,
                                   long lowerBound, Semaphore semaphore,
                                   CountDownLatch latch) {
        this.calculator = calculator;
        this.lowerBound = lowerBound;
        this.semaphore = semaphore;
        this.latch = latch;
    }

    public InfiniteSeriesSumRunner(InfiniteSeriesSumCalculator calculator) {
        this.calculator = calculator;
        this.lowerBound = 1;
    }

    @Override
    public Double call() {
        try {
            this.semaphore.acquire();
            this.infiniteSeriesSum = calculator.calculate(lowerBound, predicate);
        } catch (InterruptedException exception) {
            System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
        } finally {
            this.semaphore.release();
            this.startCountTimeAfterEnd();
        }

        return this.infiniteSeriesSum;
    }

    private void startCountTimeAfterEnd() {
        this.latch.countDown();
        try {
            this.getTime(System.currentTimeMillis());
        } catch (InterruptedException e) {
            System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
        }
    }

    private void getTime(long startTime) throws InterruptedException {
        if (this.latch.await(10, TimeUnit.MINUTES)) {
            System.out.printf("Time after the task %s end: %d ms%n",
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() - startTime);
        }
    }
}
