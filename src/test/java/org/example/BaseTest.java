package org.example;


import com.networknt.schema.*;
import org.testng.annotations.BeforeSuite;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseTest {

    Properties loadProperties;

    protected String token;

    @BeforeSuite
    public void loadProps() {
        ConfigProvider configProvider = new ConfigProvider();
        loadProperties = configProvider.loadConfig();
        token = loadProperties.getProperty("token");
    }


    public List<ValidationMessage> validatePayload(String payload, String schemaName) {
        String path = loadProperties.getProperty("path");
        Locale.setDefault(Locale.ENGLISH);
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        SchemaValidatorsConfig.Builder builder = SchemaValidatorsConfig.builder();
        SchemaValidatorsConfig config = builder.build();
        JsonSchema schema = jsonSchemaFactory.getSchema(SchemaLocation.of(path + schemaName + ".json"));
        schema.initializeValidators();
        Set<ValidationMessage> assertions = schema.validate(payload, InputFormat.JSON, executionContext -> {
            executionContext.getExecutionConfig().setFormatAssertionsEnabled(true);
        });
        List<ValidationMessage> list = assertions.stream().collect(Collectors.toList());
        return list;
    }

}
