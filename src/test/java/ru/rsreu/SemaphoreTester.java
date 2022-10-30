package ru.rsreu;

import org.junit.Assert;
import org.junit.Test;
import ru.rsreu.lab7.calculations.InfiniteSeriesSumCalculator;
import ru.rsreu.lab7.realisation.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTester {

    private static final List<Thread> tasks = new ArrayList<>();
    private static final Semaphore semaphore =
            new Semaphore(Runtime.getRuntime().availableProcessors() / 2);
    private static final TestTaskRunner task = new TestTaskRunner(
            new InfiniteSeriesSumCalculator(2500000));
    private static final int threadsNumber = Runtime.getRuntime().availableProcessors();

    @Test
    public void testSemaphoreRelease() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    counter.getAndIncrement();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                    counter.getAndDecrement();
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        Assert.assertEquals(0, counter.get());
    }

    @Test
    public void testSimultaneouslyUsedThreadsNumberInSemaphore() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch threadsNumberInSemaphoreLatch = new CountDownLatch(semaphore.getPermits());

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    if (threadsNumberInSemaphoreLatch.getCount() > 0) {
                        counter.getAndIncrement();
                    }
                    threadsNumberInSemaphoreLatch.countDown();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                    latch.countDown();
                }
            }).start();
        }

        threadsNumberInSemaphoreLatch.await();
        Assert.assertEquals(semaphore.getPermits(), counter.get());

        latch.await();
    }

    @Test
    public void testReleaseSemaphoreFunction() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch threadsNumberInSemaphoreLatch = new CountDownLatch(semaphore.getPermits() + 1);

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    if (threadsNumberInSemaphoreLatch.getCount() > 0) {
                        counter.getAndIncrement();
                    }threadsNumberInSemaphoreLatch.countDown();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                    latch.countDown();
                }
            }).start();
        }

        threadsNumberInSemaphoreLatch.await();
        Assert.assertEquals(semaphore.getPermits() + 1, counter.get());

        latch.await();
    }

    @Test
    public void testSemaphoreWorkWithException() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < threadsNumber; i++) {
            tasks.add(new Thread(() -> {
                try {
                    semaphore.acquire();
                    counter.getAndIncrement();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.printf("Thread <%s> was interrupted\n", Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                    counter.getAndDecrement();
                    latch.countDown();
                }
            }));
        }

        tasks.forEach(Thread::start);
        tasks.get(0).interrupt();

        latch.await();
        Assert.assertEquals(0, counter.get());
    }

    @Test
    public void testTryAcquireSemaphoreFunction() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(() -> {
                boolean tryAcquireResult = false;
                try {
                    tryAcquireResult = semaphore.tryAcquire();
                    if (tryAcquireResult) {
                        counter.getAndIncrement();
                    }
                    task.run();
                } finally {
                    semaphore.release();
                    if (tryAcquireResult) {
                        counter.getAndDecrement();
                    }
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        Assert.assertEquals(0, counter.get());
    }

    @Test
    public void testTryAcquireSimultaneousThreads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch threadsNumberInSemaphoreLatch = new CountDownLatch(semaphore.getPermits());

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(() -> {
                try {
                    semaphore.tryAcquire();
                    if (threadsNumberInSemaphoreLatch.getCount() > 0) {
                        counter.getAndIncrement();
                    }
                    threadsNumberInSemaphoreLatch.countDown();
                    task.run();
                } finally {
                    semaphore.release();
                    latch.countDown();
                }
            }).start();
        }

        threadsNumberInSemaphoreLatch.await();
        Assert.assertEquals(semaphore.getPermits(), counter.get());

        latch.await();
    }

}
