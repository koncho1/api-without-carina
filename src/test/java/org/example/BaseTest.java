package org.example;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public abstract class BaseTest {

    Properties loadProperties;

    HttpService httpService;

    GraphQLService graphQLService;

    JsonValidator jsonValidator;

    TestDataFactory testDataFactory;

    protected String token;

    private String apiURL;

    @BeforeMethod
    public void setUp() {
        ConfigProvider configProvider = new ConfigProvider();
        loadProperties = configProvider.loadConfig();
        token = loadProperties.getProperty("token");
        apiURL = loadProperties.getProperty("api_url");
        httpService = new HttpService(token, apiURL);
        jsonValidator = new JsonValidator(loadProperties);
        testDataFactory = new TestDataFactory(apiURL);
        graphQLService = new GraphQLService(token, apiURL);
    }


}
