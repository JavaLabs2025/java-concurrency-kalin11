package org.labs;

import org.labs.io.ParamsReader;
import org.labs.service.FoodService;
import org.labs.service.QueueService;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.labs.io.ParamsReader.EAT_COUNT;
import static org.labs.io.ParamsReader.PROGRAMMERS_COUNT;
import static org.labs.io.ParamsReader.WAITERS_COUNT;

public class Main {
    public static void main(String[] args) {
        var reader = new ParamsReader();

        var params = reader.getParamsAsMap("src/main/resources/params");
        var eatCount = params.get(EAT_COUNT);

        var queueService = new QueueService();
        var foodService = new FoodService(eatCount);

        var waiters = Executors.newFixedThreadPool(params.get(WAITERS_COUNT));
        var programmers = Executors.newFixedThreadPool(params.get(PROGRAMMERS_COUNT));

        var simulation = new Simulation(params, queueService, foodService);
        simulation.run(waiters, programmers);

        waiters.shutdown();
        programmers.shutdown();
        try {
            waiters.awaitTermination(60, TimeUnit.SECONDS);
            programmers.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Симуляция завершена. Сколько поел каждый:");
        foodService.getProgrammerIdToSoupCount().forEach((id, count) ->
                System.out.println("Программист " + id + " поел " + count + " раз"));
    }
}