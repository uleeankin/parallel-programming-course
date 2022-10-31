package ru.rsreu.lab7.realisation;

public class Semaphore {

    private final Object lock = new Object();

    private final int permits;
    private int currentPermits = 0;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    public int getPermits() {
        return this.permits;
    }

    public void acquire() throws InterruptedException {
        synchronized (lock) {
            while (this.currentPermits + 1 > this.permits) {
                lock.wait();
            }
            this.currentPermits++;
        }
    }

    public void release() {
        synchronized (lock) {
            this.currentPermits--;
            lock.notifyAll();
        }
    }

    public boolean tryAcquire() {
        synchronized (lock) {
            if (this.currentPermits + 1 <= this.permits) {
                this.currentPermits++;
                return true;
            } else {
                return false;
            }
        }
    }

}
