package org.labs.io;

import org.labs.configuration.ConfigurationParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsReader {
    private static final String SEPARATOR = "=";

    public Map<ConfigurationParam, Integer> getParamsAsMap(String filePath) {
        try (var lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(line -> line.split(SEPARATOR))
                    .collect(Collectors.toMap(
                            values -> ConfigurationParam.fromValue(values[0]),
                            values -> Integer.parseInt(values[1])
                    ));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
    }
}
