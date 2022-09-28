package ru.rsreu.lab2;

import ru.rsreu.lab2.calculations.InfiniteSeriesSumCalculator;

public class Runner {

    public static void main(String[] args) {

        long starTime = System.currentTimeMillis();
        InfiniteSeriesSumCalculator calculator = new InfiniteSeriesSumCalculator(10000000);
        double result = calculator.calculate(1, (Long x) -> Math.sin(x)/x);
        long endTime = System.currentTimeMillis();

        System.out.println("Calculation result: " + result);
        System.out.println("Calculation time: " + (endTime - starTime) / 1000.0 + " s");

    }
}
