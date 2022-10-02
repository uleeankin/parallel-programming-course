package ru.rsreu.lab4.commands;

import ru.rsreu.lab4.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab4.calculations.InfiniteSeriesSumRunner;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private long taskNumber = 0;
    private final Map<Long, Thread> executableThreads = new HashMap<>();

    public CommandManager() {

    }

    public String start(long n) {
        Thread thread = getNewThread(n);
        executableThreads.put(this.taskNumber, thread);
        thread.start();
        return Long.toString(this.taskNumber);
    }

    private Thread getNewThread(long n) {
        this.taskNumber++;
        Thread thread = new Thread( new InfiniteSeriesSumRunner(new InfiniteSeriesSumCalculator(n)));
        thread.setDaemon(true);
        thread.setName(String.format("thread[%s]", this.taskNumber));
        return thread;
    }

    public void stop(long n) {
        try {
            if (this.getThreadById(n).isAlive()) {
                this.getThreadById(n).interrupt();
            } else {
                System.out.printf("Thread <%s> isn't alive and can't be stopped%n",
                        this.getThreadById(n).getName());
            }
        } catch (NullPointerException ignored) {
        }

    }

    public void await(long n) {
        try {
            if (this.getThreadById(n).isAlive()) {
                try {
                    this.getThreadById(n).join();
                } catch (InterruptedException ignored) {
                }
            } else {
                System.out.printf("Thread <%s> isn't alive and can't be awaited%n",
                                    this.getThreadById(n).getName());
            }
        } catch(NullPointerException ignored) {
        }
    }

    public boolean exit() {
        executableThreads.clear();
        return false;
    }

    public Thread getThreadById(long threadId) {
        Thread thread = this.executableThreads.get(threadId);
        if (thread != null) {
            return thread;
        } else {
            System.out.println(String.format("Thread executing task with id %d is not found", threadId));
            return null;
        }
    }
}
