package ru.job4j.utils;

import ru.job4j.grabber.PsqlStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SqlProperties {
    public static Properties getProperties(String path) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().
                getResourceAsStream(path)) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
