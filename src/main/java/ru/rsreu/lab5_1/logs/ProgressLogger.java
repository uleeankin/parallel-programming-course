package ru.rsreu.lab5_1.logs;

public class ProgressLogger {

    private final long iterationsNumber;
    private final int logsNumber;
    private final long logsFrequency;
    private long currentIterationsNumber = 0;

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

    public synchronized void getProgress(long argument) {
        this.currentIterationsNumber += argument;
        if (this.currentIterationsNumber % this.logsFrequency == 0) {
            System.out.printf("Calculation progress: %.0f%%/100%%%n",
                    getCalculationProgress(this.currentIterationsNumber, this.iterationsNumber));
        }
    }

    private long getLogsFrequency(long iterationsNumber, int logsNumber) {
        return iterationsNumber / logsNumber;
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }
}
