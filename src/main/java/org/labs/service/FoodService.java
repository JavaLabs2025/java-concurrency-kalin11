package org.labs.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FoodService {
    private final AtomicInteger eatCount;
    private final Map<Integer, Boolean> programmerIdToSoupAvailable;
    private final Map<Integer, Integer> programmerIdToSoupCount;

    public FoodService(int eatCount) {
        this.eatCount = new AtomicInteger(eatCount);
        this.programmerIdToSoupAvailable = new ConcurrentHashMap<>();
        this.programmerIdToSoupCount = new ConcurrentHashMap<>();
    }

    public AtomicInteger getEatCount() {
        return eatCount;
    }

    public Map<Integer, Integer> getProgrammerIdToSoupCount() {
        return programmerIdToSoupCount;
    }

    public void addCount(int programmerId, int count) {
        this.programmerIdToSoupCount.put(programmerId, count);
    }

    public void initSoups(int programmersCount) {
        for (int i = 0; i < programmersCount; i++) {
            programmerIdToSoupAvailable.put(i + 1, true);
        }
    }

    public boolean hasSoup(int programmerId) {
        return programmerIdToSoupAvailable.get(programmerId);
    }

    public synchronized void addSoupToProgrammer(int programmerId) {
        if (eatCount.get() > 0) {
            programmerIdToSoupAvailable.put(programmerId, true);
            eatCount.decrementAndGet();
        }
    }

    public void disableSoup(int programmerId) {
        programmerIdToSoupAvailable.put(programmerId, false);
    }
}
