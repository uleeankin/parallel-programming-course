package ru.rsreu;

import org.junit.Assert;
import org.junit.Test;
import ru.rsreu.lab7.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab7.realisation.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreWorkPresentator {

    private static final Semaphore semaphore =
            new Semaphore(Runtime.getRuntime().availableProcessors() / 2);
    private static final TestTaskRunner task = new TestTaskRunner(
            new InfiniteSeriesSumCalculator(1000000));
    private static final int threadsNumber = Runtime.getRuntime().availableProcessors();

    @Test
    public void testAndShowSemaphoreWork() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire();
                    counter.getAndIncrement();
                    System.out.println(Thread.currentThread().getName() + " start execution");
                    task.run();
                } catch (InterruptedException e) {
                    System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                    counter.getAndDecrement();
                    System.out.println(Thread.currentThread().getName() + " end execution");
                    latch.countDown();
                }
            });
            thread.setName("Thread-" + (i + 1));
            thread.start();
        }

        latch.await();
        Assert.assertEquals(0, counter.get());
    }
}
