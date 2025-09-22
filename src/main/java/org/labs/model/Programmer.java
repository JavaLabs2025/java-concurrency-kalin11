package org.labs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

@Getter
@RequiredArgsConstructor
@Slf4j
public class Programmer implements Runnable {
    private final int id;
    @Setter
    private State state;
    @Setter
    private Fork left;
    @Setter
    private Fork right;

    private final FoodService foodService;
    private final QueueService queueService;

    @Override
    @SneakyThrows
    public void run() {
        while (foodService.getEatCount().get() > 0) {
            this.state = State.HUNGRY;
            log.info("Программист: {}, осталось еды - {}", id, foodService.getEatCount());

            while (!foodService.hasSoup(id)) {
                if (foodService.getEatCount().get() < 1) {
                    break;
                }
                log.info("Нет супа, программист с id = {} не может поесть. Он будет ждать, когда пополнится порция", id);
                if (queueService.contains(id)) {
                    log.warn("Программист {} уже в очереди", id);
                }
                if (!queueService.contains(id) && foodService.getEatCount().get() > 0) {
                    queueService.put(id);
                }

                Thread.sleep(100);
                // сказать, что нет супа и дождаться, пока он появиться, то есть кинуть поток в сон
            }

            if (foodService.hasSoup(id) && foodService.getEatCount().get() > 0) {
                try {
                    left.pickUp(id);
                    right.pickUp(id);

                    state = State.EATING;

                    log.info("Программист {} начал кушать суп", id);

                    foodService.disableSoup(id);

                } finally {
                    log.info("Программист {} закончил есть суп и собирается положить вилки", id);
                    right.pickDown(id);
                    left.pickDown(id);

                    log.info("Программист {} положил вилки", id);
                }

                log.info("Программист {} начал разговаривать", id);

                state = State.TALKING;
                Thread.sleep(100);
            }
        }
        // он должен думать
        // посмотреть, есть ли у него суп
        // если нет - попросить официанта налить и продолжить думать
        // если есть - взять ложку раз, взять ложку два, начать есть суп (если все съел - отпустить ложки и начать думать)
    }

    public enum State {
        TALKING,
        EATING,
        HUNGRY;
    }
}
