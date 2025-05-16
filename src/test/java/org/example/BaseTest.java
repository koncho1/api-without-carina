package org.example;

import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public abstract class BaseTest {

    Properties loadProperties;

    HttpService httpService;

    GraphQLService graphQLService;

    JsonValidator jsonValidator;

    TestDataFactory testDataFactory;

    protected String token;

    @BeforeSuite
    public void setUp() {
        ConfigProvider configProvider = new ConfigProvider();
        loadProperties = configProvider.loadConfig();
        token = loadProperties.getProperty("token");
        httpService = new HttpService(token);
        jsonValidator = new JsonValidator(loadProperties);
        testDataFactory = new TestDataFactory();
        graphQLService = new GraphQLService(token);
    }


}
