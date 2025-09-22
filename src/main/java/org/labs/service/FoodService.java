package org.labs.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Slf4j
public class FoodService {
    private final AtomicInteger eatCount;
    private final Map<Integer, Boolean> programmerIdToSoupAvailable = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> programmerIdToSoupCount = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public FoodService(int eatCount) {
        this.eatCount = new AtomicInteger(eatCount);
    }

    public void incrementSoupCountByProgrammerId(int programmerId) {
        this.programmerIdToSoupCount.put(
                programmerId,
                programmerIdToSoupCount.getOrDefault(programmerId, 0) + 1
        );
    }

    public void initSoups(int programmersCount) {
        for (int i = 0; i < programmersCount; i++) {
            programmerIdToSoupAvailable.put(i + 1, false);
        }
    }

    public boolean hasSoup(int programmerId) {
        return programmerIdToSoupAvailable.get(programmerId);
    }

    public boolean addSoupToProgrammer(int programmerId) {
        if (eatCount.get() == 0) {
            return false;
        }
        eatCount.decrementAndGet();
        programmerIdToSoupAvailable.put(programmerId, true);
        log.info("Программист {} получил порцию супа. Осталось порций: {}", programmerId, eatCount.get());
        incrementSoupCountByProgrammerId(programmerId);
        getProgrammerIdToSoupCount().forEach((id, count) ->
                log.warn("Программист {} поел {} раз", id, count));
        return true;
    }

    public void disableSoup(int programmerId) {
        log.info("Программист {} съел порцию супа", programmerId);
        programmerIdToSoupAvailable.put(programmerId, false);
    }
}
