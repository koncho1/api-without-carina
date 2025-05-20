package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;
import org.example.models.User;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonValidator {

    private static final int NO_ID = 0;

    Properties properties;

    public JsonValidator(Properties properties) {
        this.properties = properties;
    }

    public List<ValidationMessage> validateSchema(String payload, String schemaName) {
        String path = properties.getProperty("path");
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema schema = jsonSchemaFactory.getSchema(SchemaLocation.of(path + schemaName + ".json"));
        schema.initializeValidators();
        Set<ValidationMessage> assertions = schema.validate(payload, InputFormat.JSON, executionContext -> {
            executionContext.getExecutionConfig().setFormatAssertionsEnabled(true);
        });
        List<ValidationMessage> list = assertions.stream().collect(Collectors.toList());
        return list;
    }

    public boolean validateUser(String responseBody, User sentUser) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User receivedUser = mapper.readValue(responseBody, User.class);
        if (sentUser.getId() == NO_ID) {
            sentUser.setId(receivedUser.getId());
        }
        return sentUser.equals(receivedUser);
    }

}
