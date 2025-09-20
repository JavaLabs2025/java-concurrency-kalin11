package org.labs;

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
import static org.labs.io.ParamsReader.WAITERS_COUNT;

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
        System.out.println("Начали симуляцию");
        foodService.initSoups(params.get(PROGRAMMERS_COUNT));
        fillForks(params.get(PROGRAMMERS_COUNT));
        fillProgrammers(params.get(PROGRAMMERS_COUNT), programmers);
        createWaiters(params.get(WAITERS_COUNT), waiters);
    }

    private void fillForks(int forksCount) {
        for (int i = 0; i < forksCount; i++) {
            forks.add(new Fork(i + 1, new ReentrantLock()));
        }
    }

    private void fillProgrammers(int programmersCount, ExecutorService executor) {
        for (int i = 0; i < programmersCount; i++) {
            var programmer = new Programmer(i + 1, foodService, queueService);
            programmer.setState(Programmer.State.HUNGRY);
            var left = forks.get(i);
            var right = forks.get((i + 1) % programmersCount);
            programmer.setLeft(left.id() < right.id() ? left : right);
            programmer.setRight(left.id() < right.id() ? right : left);
            executor.submit(programmer);
        }
    }

    private void createWaiters(int waitersCount, ExecutorService executor) {
        for (int i = 0; i < waitersCount; i++) {
            executor.submit(new Waiter(i + 1, queueService, foodService));
        }
    }
}
