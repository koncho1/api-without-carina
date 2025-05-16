package org.example;

import com.networknt.schema.ValidationMessage;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphQLUserTest extends BaseTest {

    @Test
    public void getUsersCountTest() throws IOException, ParseException {
        ClassicHttpResponse response = graphQLService.graphqlGetUserCount();
        String responseBody = EntityUtils.toString(response.getEntity());
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "graphqlGetUsersCountTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());

    }

    @Test
    public void getUserTest() throws IOException, ParseException {
        ClassicHttpResponse response = graphQLService.graphqlGetUserById(testDataFactory.getId());
        String responseBody = EntityUtils.toString(response.getEntity());
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "graphqlGetUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }


    @Test
    public void deleteUserTest() throws IOException, ParseException {
        ClassicHttpResponse response = graphQLService.graphqlDeleteUser(testDataFactory.getId());
        String responseBody = EntityUtils.toString(response.getEntity());
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "graphqlDeleteUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void createUserTest() throws IOException, ParseException {
        ClassicHttpResponse response = graphQLService.graphqlCreateUser(testDataFactory.generateTestUser());
        String responseBody = EntityUtils.toString(response.getEntity());
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "graphqlCreateUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void updateUserTest() throws IOException, ParseException {
        ClassicHttpResponse response = graphQLService.graphqlUpdateUser(testDataFactory.generateTestUser());
        String responseBody = EntityUtils.toString(response.getEntity());
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "graphqlUpdateUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

}
