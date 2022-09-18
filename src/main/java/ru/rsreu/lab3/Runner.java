package ru.rsreu.lab3;

import ru.rsreu.lab3.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab3.predicates.InfinityConvergentSeriesPredicate;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        InfiniteSeriesSumCalculator calculator = new InfiniteSeriesSumCalculator(3.3E-14);

        long starTime = System.currentTimeMillis();
        InfiniteSeriesSumRunner runner = new InfiniteSeriesSumRunner(calculator, 1,
                                                                    new InfinityConvergentSeriesPredicate());
        Thread thread = new Thread(runner);
        thread.start();
        thread.join();
        long endTime = System.currentTimeMillis();

        System.out.println("Calculation result: " + runner.getInfiniteSeriesSum());
        System.out.println("Calculation time: " + (endTime - starTime) / 1000.0 + " s");
    }
}
