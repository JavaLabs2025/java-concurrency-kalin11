package org.labs;

import org.labs.io.ParamsReader;
import org.labs.model.Programmer;

import static org.labs.io.ParamsReader.PROGRAMMERS_COUNT;

public class Main {
    public static void main(String[] args) {
        var reader = new ParamsReader();

        var params = reader.getParamsAsMap("src/main/resources/params");
        var programmersCount = params.get(PROGRAMMERS_COUNT);

        System.out.println(params.toString());

        test(programmersCount);
    }

    private static void test(int programmersCount) {
        for (int i = 0; i < programmersCount; i++) {
            var programmer = new Programmer(i + 1, null, null);
            programmer.run();
        }
    }
}