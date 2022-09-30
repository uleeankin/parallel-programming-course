package ru.rsreu.lab4.logs;

public class ProgressLogger {

    private final long iterationsNumber;
    private final int logsNumber;
    private final long logsFrequency;

    public ProgressLogger(long iterationsNumber, int logsNumber) {
        this.iterationsNumber = iterationsNumber;
        this.logsNumber = logsNumber;
        this.logsFrequency = this.getLogsFrequency(this.iterationsNumber, this.logsNumber);
    }

    public ProgressLogger(long iterationsNumber) {
        this.iterationsNumber = iterationsNumber;
        this.logsNumber = 10;
        this.logsFrequency = this.getLogsFrequency(this.iterationsNumber, this.logsNumber);
    }

    public void getProgress(long argument) {
        if (argument % this.logsFrequency == 0) {
            System.out.printf("Calculation progress of %s: %.0f%%/100%%%n",
                    Thread.currentThread().getName(),
                    getCalculationProgress(argument, this.iterationsNumber));
        }
    }

    private long getLogsFrequency(long iterationsNumber, int logsNumber) {
        return iterationsNumber / logsNumber;
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }
}
