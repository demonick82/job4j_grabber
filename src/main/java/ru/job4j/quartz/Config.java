package ru.job4j.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties getProperties(String path) {
        Properties config = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().
                getResourceAsStream(path)) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
