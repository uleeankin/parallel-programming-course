package ru.rsreu.lab7.logs;

import java.util.concurrent.locks.ReentrantLock;

public class ProgressLogger {

    private final long iterationsNumber;
    private final int logsNumber;
    private final long logsFrequency;
    private long currentIterationsNumber = 0;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    public ProgressLogger(long iterationsNumber, int logsNumber) {
        this.iterationsNumber = iterationsNumber;
        this.logsNumber = logsNumber;
        this.logsFrequency = this.getLogsFrequency(this.iterationsNumber, this.logsNumber);
    }

    public ProgressLogger(long iterationsNumber) {
        this.iterationsNumber = iterationsNumber;
        this.logsNumber = 5;
        this.logsFrequency = this.getLogsFrequency(this.iterationsNumber, this.logsNumber);
    }

    public void getProgress(long argument) {
        this.reentrantLock.lock();
        this.currentIterationsNumber += argument;
        System.out.printf("Calculation progress: %.0f%%/100%%%n",
                getCalculationProgress(this.currentIterationsNumber, this.iterationsNumber));
        this.reentrantLock.unlock();
    }

    private long getLogsFrequency(long iterationsNumber, int logsNumber) {
        return iterationsNumber / logsNumber;
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }
}
