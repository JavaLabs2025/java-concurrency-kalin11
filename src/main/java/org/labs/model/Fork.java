package org.labs.model;

import java.util.concurrent.locks.Lock;

public record Fork(
    int id,
    Lock lock
) {
    public void pickUp(int programmerId) {
        lock.lock();
        System.out.println("Программист " + programmerId + " взял вилку с id " + id);
    }

    public void pickDown(int programmerId) {
        lock.unlock();
        System.out.println("Программист " + programmerId + " положил вилку с id " + id);
    }
}
