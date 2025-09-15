package org.labs.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParamsReader {
    public static final String PROGRAMMERS_COUNT = "programmers_count";
    public static final String EAT_COUNT = "eat_count";
    public static final String WAITERS_COUNT = "waiters_count";

    private static final Set<String> PARAMS_NAMES = Set.of(PROGRAMMERS_COUNT, EAT_COUNT, WAITERS_COUNT);

    private static final String SEPARATOR = "=";

    public Map<String, Integer> getParamsAsMap(String filePath) {
        try (var lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(line -> line.split(SEPARATOR))
                    .filter(val -> PARAMS_NAMES.contains(val[0]))
                    .collect(Collectors.toMap(
                            values -> values[0],
                            values -> Integer.parseInt(values[1])
                    ));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
    }
}
