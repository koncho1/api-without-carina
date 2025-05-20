package org.example;


import com.networknt.schema.ValidationMessage;
import org.apache.hc.core5.http.*;
import org.example.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;


public class UserTest extends BaseTest {

    private static final int GET_ALL_USERS_ID = 0;

    private static final int NON_EXISTENT_ID = -1;

    private static final String INCORRECT_FORMAT_EMAIL = "johnmail.com";


    @Test
    public void testGetUsers() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendGetRequest(GET_ALL_USERS_ID);
        int responseCode = response.statusCode();
        String responseBody = response.body();
        Assert.assertEquals(responseCode, HttpStatus.SC_SUCCESS, "Wrong response code. Expected: " + HttpStatus.SC_SUCCESS + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "getUsersTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testGetUser() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendGetRequest(testDataFactory.getId());
        int responseCode = response.statusCode();
        String responseBody = response.body();
        Assert.assertEquals(responseCode, HttpStatus.SC_SUCCESS, "Wrong response code. Expected: " + HttpStatus.SC_SUCCESS + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "getUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testGetNonExistentUser() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendGetRequest(NON_EXISTENT_ID);
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, HttpStatus.SC_NOT_FOUND, "Wrong response code. Expected: " + HttpStatus.SC_NOT_FOUND + " Received: " + responseCode);
    }

    @Test
    public void testCreateUser() throws IOException, URISyntaxException, InterruptedException {
        User user = testDataFactory.generateTestUser();
        HttpResponse<String> response = httpService.sendPostRequest(user);
        int responseCode = response.statusCode();
        String responseBody = response.body();
        Assert.assertEquals(responseCode, HttpStatus.SC_CREATED, "Wrong response code. Expected: " + HttpStatus.SC_CREATED + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "postUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
        Assert.assertTrue(jsonValidator.validateUser(responseBody, user), "The received user is not the same as the sent user");
    }

    @Test
    public void testCreateUserWithIncorrectParams() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendPostRequestWithInvalidEmail();
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, HttpStatus.SC_UNPROCESSABLE_CONTENT, "Wrong response code. Expected: " + HttpStatus.SC_UNPROCESSABLE_CONTENT + " Received: " + responseCode);
    }

    @Test
    public void testCreateUserWithIncorrectMailFormat() throws IOException, URISyntaxException, InterruptedException {
        User user = testDataFactory.generateTestUser();
        user.setEmail(INCORRECT_FORMAT_EMAIL);
        HttpResponse<String> response = httpService.sendPostRequest(user);
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, HttpStatus.SC_UNPROCESSABLE_CONTENT, "Wrong response code. Expected: " + HttpStatus.SC_UNPROCESSABLE_CONTENT + " Received: " + responseCode);
    }

    @Test
    public void testPatchUser() throws IOException, URISyntaxException, InterruptedException {
        User user = testDataFactory.generateTestUser();
        user.setId(testDataFactory.getId());
        HttpResponse<String> response = httpService.sendPatchRequest(user);
        int responseCode = response.statusCode();
        String responseBody = response.body();
        Assert.assertEquals(responseCode, HttpStatus.SC_SUCCESS, "Wrong response code. Expected: " + HttpStatus.SC_SUCCESS + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "patchUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
        Assert.assertTrue(jsonValidator.validateUser(responseBody, user), "The received user is not the same as the sent user");
    }

    @Test
    public void testPutUser() throws IOException, URISyntaxException, InterruptedException {
        User user = testDataFactory.generateTestUser();
        user.setId(testDataFactory.getId());
        HttpResponse<String> response = httpService.sendPutRequest(user);
        int responseCode = response.statusCode();
        String responseBody = response.body();
        Assert.assertEquals(responseCode, HttpStatus.SC_SUCCESS, "Wrong response code. Expected: " + HttpStatus.SC_SUCCESS + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validateSchema(responseBody, "putUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
        Assert.assertTrue(jsonValidator.validateUser(responseBody, user), "The received user is not the same as the sent user");
    }

    @Test
    public void testDeleteUser() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendDeleteRequest(testDataFactory.getId());
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, HttpStatus.SC_NO_CONTENT, "Wrong response code. Expected: " + HttpStatus.SC_NO_CONTENT + " Received: " + responseCode);
    }

    @Test
    public void testDeleteUserWithoutAccessToken() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = httpService.sendDeleteRequestWithoutToken(testDataFactory.getId());
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, HttpStatus.SC_UNAUTHORIZED, "Wrong response code. Expected: " + HttpStatus.SC_UNAUTHORIZED + " Received: " + responseCode);
    }

}
