package org.example;


import com.networknt.schema.ValidationMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//TODO:
//add payload validation against json schema

public class UserTest extends BaseTest {
    @Test
    public void getUsers() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest request = new HttpGet("https://gorest.co.in/public/v2/users");
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        List<ValidationMessage> errors = validatePayload(response, "getUsersTemplate");
        Assert.assertTrue(errors.isEmpty());

    }

    @Test
    public void getUser() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest request = new HttpGet("https://gorest.co.in/public/v2/users/7881661");
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        List<ValidationMessage> errors = validatePayload(response, "getUserTemplate");
        Assert.assertTrue(errors.isEmpty());
    }

    @Test
    public void getNonExistentUser() throws IOException {
        HttpUriRequest request = new HttpGet("https://gorest.co.in/public/v2/users/1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(), 404);
    }

    @Test
    void postUser() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format("https://gorest.co.in/public/v2/users"));
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", String.format("%s@gmail.com", RandomStringUtils.random(10, true, true))));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPost.setHeader(header);
        String response = client.execute(httpPost, new BasicHttpClientResponseHandler());
        List<ValidationMessage> errors = validatePayload(response, "postUserTemplate");
        Assert.assertTrue(errors.isEmpty());
    }

    @Test
    void postUserWithIncorrectParams() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format("https://gorest.co.in/public/v2/users"));
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("mail", String.format("%s@gmail.com", RandomStringUtils.random(10, true, true))));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPost.setHeader(header);
        HttpResponse response = httpclient.execute(httpPost);
        Assert.assertEquals(response.getCode(), 422);
    }

    @Test
    void postUserWithIncorrectMailFormat() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format("https://gorest.co.in/public/v2/users?access-token=%s", token));
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "Mary"));
        params.add(new BasicNameValuePair("mail", "marygmail.com"));
        params.add(new BasicNameValuePair("gender", "female"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPost.setHeader(header);
        HttpResponse response = httpclient.execute(httpPost);
        Assert.assertEquals(response.getCode(), 422);
    }

    @Test
    public void patchUser() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(String.format("https://gorest.co.in/public/v2/users/7887693?access-token=%s", token));
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", String.format("%s@gmail.com", RandomStringUtils.random(10, true, true))));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPatch.setEntity(new UrlEncodedFormEntity(params));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPatch.setHeader(header);
        String response = client.execute(httpPatch, new BasicHttpClientResponseHandler());
        List<ValidationMessage> errors = validatePayload(response, "patchUserTemplate");
        Assert.assertTrue(errors.isEmpty());
    }

    @Test
    public void putUser() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(String.format("https://gorest.co.in/public/v2/users/7887693?access-token=%s", token));
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", String.format("%s@gmail.com", RandomStringUtils.random(10, true, true))));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPut.setEntity(new UrlEncodedFormEntity(params));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPut.setHeader(header);
        String response = client.execute(httpPut, new BasicHttpClientResponseHandler());
        List<ValidationMessage> errors = validatePayload(response, "putUserTemplate");
        Assert.assertTrue(errors.isEmpty());
    }

    @Test
    public void deleteUser() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest request = new HttpDelete(String.format("https://gorest.co.in/public/v2/users/7887693?access-token=%s", token));
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(header);
        HttpResponse httpResponse = client.execute(request);
        Assert.assertEquals(httpResponse.getCode(), 204);
    }

    @Test
    public void deleteWithoutToken() throws IOException {
        HttpUriRequest request = new HttpDelete("https://gorest.co.in/public/v2/users/7881649");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(), 401);
    }
}
