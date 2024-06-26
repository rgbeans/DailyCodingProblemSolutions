package com.rgbeans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Dce548Test {

    @ParameterizedTest
    @MethodSource("getData")
    public void test1(String time, int expectedAngle, boolean expectedToThrow) {
        if (expectedToThrow) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> Dce548.timeToAngle(time));
        } else {
            int angle = Dce548.timeToAngle(time);
            Assertions.assertEquals(expectedAngle, angle);
        }
    }

    public static Stream<Object[]> getData() throws IOException {
        return Files.lines(Path.of(System.getProperty("user.dir"), "..", "..", "TestResources", "Dce548.csv").normalize())
                .filter(line -> !line.isBlank())
                .map(line -> line.split(","))
                .filter(columns -> columns.length == 3)
                .map(columns -> new Object[]{columns[0], Integer.parseInt(columns[1]), Boolean.parseBoolean(columns[2])});
    }
}