package ru.rsreu.lab6.logs;

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
        if (this.iterationsNumber <= this.logsNumber) {
            outputProgress(argument);
        } else {
            if (argument % this.logsFrequency == 0) {
                outputProgress(argument);
            }
        }
    }

    private void outputProgress(long argument) {
        System.out.printf("Calculation progress: %.0f%%/100%%%n",
                getCalculationProgress(argument, this.iterationsNumber));
    }

    private long getLogsFrequency(long iterationsNumber, int logsNumber) {
        return iterationsNumber <= logsNumber ? iterationsNumber : iterationsNumber / logsNumber;
    }

    private double getCalculationProgress(long argument, long iterationsNumber) {
        return argument * 100.0 / iterationsNumber;
    }
}
