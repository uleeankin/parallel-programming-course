package ru.rsreu.lab2.predicates;

import ru.rsreu.lab2.predicates.Predicate;

public class InfinityConvergentSeriesPredicate implements Predicate {
    
    @Override
    public Double calculate(Long x) {
        return (double) 1/x * Math.sin(x);
    }
}
