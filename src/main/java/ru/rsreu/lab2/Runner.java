package ru.rsreu.lab2;

import ru.rsreu.lab2.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab2.predicates.InfinityConvergentSeriesPredicate;

public class Runner {

    public static void main(String[] args) {

        long starTime = System.currentTimeMillis();
        InfiniteSeriesSumCalculator calculator = new InfiniteSeriesSumCalculator(1E-14);
        double result = calculator.calculate(1, Long.MAX_VALUE, new InfinityConvergentSeriesPredicate());
        long endTime = System.currentTimeMillis();

        System.out.println("Calculation result: " + result);
        System.out.println("Calculation time: " + (endTime - starTime) / 1000.0 + " s");

    }
}
