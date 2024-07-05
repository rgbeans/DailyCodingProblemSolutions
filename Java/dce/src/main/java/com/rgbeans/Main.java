package com.rgbeans;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println(Path.of(System.getProperty("user.dir"), "TestResources", "Dce548.csv"));
    }
}