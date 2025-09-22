package org.labs.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

@Slf4j
public record Fork(
    int id,
    Lock lock
) {
    public void pickUp(int programmerId) {
        lock.lock();
        log.info("Программист {} взял вилку с id {}", programmerId, id);
    }

    public void pickDown(int programmerId) {
        lock.unlock();
        log.info("Программист {} положил вилку с id {}", programmerId, id);
    }
}
