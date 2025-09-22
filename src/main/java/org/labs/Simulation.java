package org.labs;

import lombok.extern.slf4j.Slf4j;
import org.labs.model.Fork;
import org.labs.model.Programmer;
import org.labs.model.Waiter;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import static org.labs.io.ParamsReader.PROGRAMMERS_COUNT;
import static org.labs.io.ParamsReader.TIMEOUT_MS;
import static org.labs.io.ParamsReader.WAITERS_COUNT;

@Slf4j
public class Simulation {
    private final Map<String, Integer> params;
    private final QueueService queueService;
    private final FoodService foodService;

    private final List<Fork> forks;

    public Simulation(Map<String, Integer> params, QueueService queueService, FoodService foodService) {
        this.params = params;
        this.queueService = queueService;
        this.foodService = foodService;
        this.forks = new ArrayList<>();
    }

    public void run(ExecutorService waiters, ExecutorService programmers) {
        log.info("Начали симуляцию");
        foodService.initSoups(params.get(PROGRAMMERS_COUNT));
        fillForks(params.get(PROGRAMMERS_COUNT));
        fillProgrammers(params.get(PROGRAMMERS_COUNT), programmers, params.get(TIMEOUT_MS));
        createWaiters(params.get(WAITERS_COUNT), waiters, params.get(TIMEOUT_MS));
    }

    private void fillForks(int forksCount) {
        for (int i = 0; i < forksCount; i++) {
            forks.add(new Fork(i + 1, new ReentrantLock()));
        }
    }

    private void fillProgrammers(int programmersCount, ExecutorService executor, long timeoutMs) {
        for (int i = 0; i < programmersCount; i++) {
            var programmer = new Programmer(i + 1, foodService, queueService, timeoutMs);
            programmer.setState(Programmer.State.HUNGRY);
            var left = forks.get(i);
            var right = forks.get((i + 1) % programmersCount);
            programmer.setLeft(left.id() < right.id() ? left : right);
            programmer.setRight(left.id() < right.id() ? right : left);
            executor.submit(programmer);
        }
    }

    private void createWaiters(int waitersCount, ExecutorService executor, long timeoutMs) {
        for (int i = 0; i < waitersCount; i++) {
            executor.submit(new Waiter(i + 1, queueService, foodService, timeoutMs));
        }
    }
}

// 1 3 5 4 2 1 5 3 4 2