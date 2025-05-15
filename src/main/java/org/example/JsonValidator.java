package org.example;

import com.networknt.schema.*;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonValidator {

    Properties properties;

    public JsonValidator(Properties properties) {
        this.properties = properties;
    }

    public List<ValidationMessage> validatePayload(String payload, String schemaName) {
        String path = properties.getProperty("path");
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
