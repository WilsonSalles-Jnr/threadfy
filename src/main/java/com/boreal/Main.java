
package com.boreal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void main() {
        boolean threadfy = Boolean.TRUE;
        int primeLimit = 20_000_000;
        int loopSize = 10;
        int threadSize = 10;

        long startTime = System.currentTimeMillis();

        if (threadfy) {
            withThread(primeLimit, loopSize,threadSize);
        } else {
            withoutThread(primeLimit, loopSize);
        }

        System.out.printf("Total time: %d ms%n",System.currentTimeMillis() - startTime);
    }

    private static void withThread(int primeLimit, int loopSize, int threadSize) {
        try (ExecutorService executor = Executors.newFixedThreadPool(threadSize)) {

            for (int i = 1; i <= loopSize; i++) {
                final int number = i;

                executor.submit(() -> {
                    long startTime = System.currentTimeMillis();

                    System.out.printf("Task %d begin%n", number);

                    long prime = primeCount(primeLimit);
                    long duration = System.currentTimeMillis() - startTime;

                    System.out.printf("Task %d finished: %,d primes in %d ms%n", number, prime, duration);
                });
            }
        }
    }

    private static void withoutThread(int primeLimit, int loopSize) {
        for (int i = 1; i <= loopSize; i++) {
            long startTime = System.currentTimeMillis();

            var prime = primeCount(primeLimit);
            long duration = System.currentTimeMillis() - startTime;

            System.out.printf("Task finished: %,d primes in %d ms%n", prime, duration);
        }
    }

    private static long primeCount(int limit) {
        long quantity = 0;

        for (int number = 2; number <= limit; number++) {
            boolean prime = true;

            for (int divisor = 2; divisor <= number / divisor; divisor++) {
                if (number % divisor == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime) quantity++;
        }

        return quantity;
    }
}
