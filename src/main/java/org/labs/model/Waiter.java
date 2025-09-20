package org.labs.model;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

@RequiredArgsConstructor
public class Waiter implements Runnable {
    private final int id;
    private final QueueService queueService;
    private final FoodService foodService;

    @Override
    @SneakyThrows
    public void run() {
        while (foodService.getEatCount().get() > 0) {
            System.out.println("Официант " + id + ", его очередь = ");
            queueService.print();
            var programmerId = queueService.take();
            System.out.println("Официант " + id + " взял программиста с id = " + programmerId);
            if (programmerId != null) {
                foodService.addSoupToProgrammer(programmerId);
                System.out.println("Официант " + id + " добавил программисту " + programmerId + " суп");
            }
            Thread.sleep(100);
        }
    }
}
