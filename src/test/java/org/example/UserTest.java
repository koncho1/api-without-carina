package org.example;


import com.networknt.schema.ValidationMessage;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.List;


public class UserTest extends BaseTest {

    private static final int NO_SPECIFIC_ID = 0;

    private static final int NON_EXISTENT_ID = 1;

    private static final int HTTP_NOTFOUND = 404;

    private static final int HTTP_UNPROCESSABLECONTENT = 422;

    private static final int HTTP_NOCONTENTSUCCESS = 204;

    private static final int HTTP_OK = 200;

    private static final int HTTP_CREATED = 201;

    private static final int HTTP_UNAUTHORIZED = 401;

    @Test
    public void testGetUsers() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendGetRequest(NO_SPECIFIC_ID);
        int responseCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(responseCode, HTTP_OK, "Wrong response code. Expected: " + HTTP_OK + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "getUsersTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testGetUser() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendGetRequest(testDataFactory.getId());
        int responseCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(responseCode, HTTP_OK, "Wrong response code. Expected: " + HTTP_OK + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "getUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testGetNonExistentUser() throws IOException {
        ClassicHttpResponse response = httpService.sendGetRequest(NON_EXISTENT_ID);
        int responseCode = response.getCode();
        Assert.assertEquals(responseCode, HTTP_NOTFOUND, "Wrong response code. Expected: " + HTTP_NOTFOUND + " Received: " + responseCode);
    }

    @Test
    void testCreateUser() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendPostRequest(testDataFactory.generateTestUser());
        int responseCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(responseCode, HTTP_CREATED, "Wrong response code. Expected: " + HTTP_CREATED + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "postUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    void testCreateUserWithIncorrectParams() throws IOException {
        ClassicHttpResponse response = httpService.sendPostRequestWithIncorrectParams();
        int responseCode = response.getCode();
        Assert.assertEquals(responseCode, HTTP_UNPROCESSABLECONTENT, "Wrong response code. Expected: " + HTTP_UNPROCESSABLECONTENT + " Received: " + responseCode);
    }

    @Test
    void testCreateUserWithIncorrectMailFormat() throws IOException, ParseException {
        User user = testDataFactory.generateTestUser();
        user.setEmail("johnmail.com");
        ClassicHttpResponse response = httpService.sendPostRequest(user);
        int responseCode = response.getCode();
        Assert.assertEquals(responseCode, HTTP_UNPROCESSABLECONTENT, "Wrong response code. Expected: " + HTTP_UNPROCESSABLECONTENT + " Received: " + responseCode);
    }

    @Test
    public void testPatchUser() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendPatchRequest(testDataFactory.generateTestUser());
        int responseCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(responseCode, HTTP_OK, "Wrong response code. Expected: " + HTTP_OK + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "patchUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testPutUser() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendPutRequest(testDataFactory.generateTestUser());
        int responseCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(responseCode, HTTP_OK, "Wrong response code. Expected: " + HTTP_OK + " Received: " + responseCode);
        List<ValidationMessage> errors = jsonValidator.validatePayload(responseBody, "putUserTemplate");
        Assert.assertTrue(errors.isEmpty(), errors.toString());
    }

    @Test
    public void testDeleteUser() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendDeleteRequest(testDataFactory.getId());
        int responseCode = response.getCode();
        Assert.assertEquals(responseCode, HTTP_NOCONTENTSUCCESS, "Wrong response code. Expected: " + HTTP_NOCONTENTSUCCESS + " Received: " + responseCode);
    }

    @Test
    public void testDeleteUserWithoutAccessToken() throws IOException, ParseException {
        ClassicHttpResponse response = httpService.sendDeleteRequestWithoutToken(testDataFactory.getId());
        int responseCode = response.getCode();
        Assert.assertEquals(responseCode, HTTP_UNAUTHORIZED, "Wrong response code. Expected: " + HTTP_UNAUTHORIZED + " Received: " + responseCode);
    }

}
