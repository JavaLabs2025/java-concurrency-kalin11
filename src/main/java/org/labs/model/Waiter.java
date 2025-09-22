package org.labs.model;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

@RequiredArgsConstructor
@Slf4j
public class Waiter implements Runnable {
    private final int id;
    private final QueueService queueService;
    private final FoodService foodService;
    private final long timeoutMs;

    @Override
    @SneakyThrows
    public void run() {
        while (foodService.getEatCount().get() > 0) {
            log.info("Официант {}, его очередь = {}", id, queueService.print());
            var programmerId = queueService.poll();
            log.info("Официант {} взял программиста с id = {}", id, programmerId);
            if (programmerId != null) {
                if (foodService.addSoupToProgrammer(programmerId)) {
                    log.info("Официант {} добавил программисту {} суп", id, programmerId);
                } else {
                    break;
                }
            }
            Thread.sleep(timeoutMs);
        }
    }
}
