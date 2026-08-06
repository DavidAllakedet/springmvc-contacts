package com.contact.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final Properties properties;
    public PropertiesReader(String fileName) {
        properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) properties.load(is);
            else throw new RuntimeException("Fichier " + fileName + " non trouve");
        } catch (IOException e) { throw new RuntimeException(e); }
    }
    public String getProperty(String key) { return properties.getProperty(key); }
}
