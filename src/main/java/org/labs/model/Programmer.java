package org.labs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@RequiredArgsConstructor
public class Programmer implements Runnable {
    private final int id;
    @Setter
    private State state;
    @Setter
    private Fork left;
    @Setter
    private Fork right;

    private AtomicInteger ateSoups = new AtomicInteger(0);

    private final FoodService foodService;
    private final QueueService queueService;

    @Override
    @SneakyThrows
    public void run() {
        while (foodService.getEatCount().get() > 0) {
            this.state = State.HUNGRY;

            while (!foodService.hasSoup(id)) {
                if (foodService.getEatCount().get() < 1) {
                    break;
                }

                System.out.println("Нет супа, программист с id = " + id + " не может поесть. Он будет ждать, когда пополнится порция");
                if (!queueService.contains(id)) {
                    queueService.put(id);
                }
                Thread.sleep(100);
                // сказать, что нет супа и дождаться, пока он появиться, то есть кинуть поток в сон
            }

            try {
                left.pickUp(id);
                right.pickUp(id);
                this.state = State.EATING;
                System.out.println("Программист " + id + " начал кушать суп");
                foodService.disableSoup(id);
                ateSoups.incrementAndGet();
                // Здесь можно добавить Thread.sleep() для симуляции времени еды, если нужно
            } finally {
                right.pickDown(id);
                left.pickDown(id);
                System.out.println("Программист " + id + " положил вилки");
            }

            System.out.println("Программист " + id + " начал разговаривать");
            this.state = State.TALKING;
            Thread.sleep(100);

            System.out.println("Осталось еды - " + foodService.getEatCount());
        }

        foodService.addCount(id, ateSoups.get());
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
