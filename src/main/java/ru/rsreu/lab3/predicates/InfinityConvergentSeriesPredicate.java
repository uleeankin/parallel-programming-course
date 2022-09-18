package ru.rsreu.lab3.predicates;

public class InfinityConvergentSeriesPredicate implements Predicate {
    
    @Override
    public double calculate(long x) {
        return 1.0/x * Math.sin(x);
    }
}
