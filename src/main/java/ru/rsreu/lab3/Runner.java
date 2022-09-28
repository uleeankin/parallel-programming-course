package ru.rsreu.lab3;

import ru.rsreu.lab3.calculations.InfiniteSeriesSumCalculator;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        InfiniteSeriesSumCalculator calculator = new InfiniteSeriesSumCalculator(10000000);
        InfiniteSeriesSumRunner runner = new InfiniteSeriesSumRunner(calculator, 1,
                                                                    (Long x) -> Math.sin(x) / x);
        Thread thread = new Thread(runner);
        long starTime = System.currentTimeMillis();
        thread.start();
        thread.join();
        long endTime = System.currentTimeMillis();

        System.out.println("Calculation result: " + runner.getInfiniteSeriesSum());
        System.out.println("Calculation time: " + (endTime - starTime) / 1000.0 + " s");
    }
}
