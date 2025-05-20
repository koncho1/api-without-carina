package org.example;

import com.networknt.schema.ValidationMessage;
import org.apache.hc.core5.http.ParseException;
import org.example.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class GraphQLUserTest extends BaseTest {

    @Test
    public void getUsersCountTest() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = graphQLService.getUserCount();
        String responseBody = response.body();
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "graphqlGetUsersCountTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());

    }

    @Test
    public void getUserTest() throws IOException, URISyntaxException, InterruptedException, ParseException {
        HttpResponse<String> response = graphQLService.getUserById(testDataFactory.getId());
        String responseBody = response.body();
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "graphqlGetUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }


    @Test
    public void deleteUserTest() throws IOException, URISyntaxException, InterruptedException, ParseException {
        HttpResponse<String> response = graphQLService.deleteUser(testDataFactory.getId());
        String responseBody = response.body();
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "graphqlDeleteUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void createUserTest() throws IOException, URISyntaxException, InterruptedException, ParseException {
        HttpResponse<String> response = graphQLService.createUser(testDataFactory.generateTestUser());
        String responseBody = response.body();
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "graphqlCreateUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void updateUserTest() throws IOException, URISyntaxException, InterruptedException, ParseException {
        User user = testDataFactory.generateTestUser();
        user.setId(testDataFactory.getId());
        HttpResponse<String> response = graphQLService.updateUser(user);
        String responseBody = response.body();
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "graphqlUpdateUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

}
