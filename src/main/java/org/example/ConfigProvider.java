package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigProvider {

    private static Logger logger = LoggerFactory.getLogger(ConfigProvider.class);

    private static final String TEST_FILE_PATH = "src/main/java/org/example/resources/data.properties";

    Properties properties = new Properties();

    private void loadProps() {

        try (InputStream files = Files.newInputStream(Paths.get(TEST_FILE_PATH))) {
            properties.load(files);
        } catch (IOException e) {
            logger.info("An error loading the file has occurred");
        }
    }

    public Properties loadConfig() {
        loadProps();
        return properties;
    }
}
