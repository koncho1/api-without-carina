package org.example;


import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserTest
{
    @Test
    public void getUsers() throws ClientProtocolException, IOException {
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest request=new HttpGet("https://gorest.co.in/public/v2/users");
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        System.out.println(response);
        //JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();

    }

    @Test
    public void getUser() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet("https://gorest.co.in/public/v2/users/7824865");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(), 200);
    }

    @Test void postUser() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://gorest.co.in/public/v2/users?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", "12345"));
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", "mail@gmail.com"));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httpPost);
        Assert.assertEquals(response.getCode(), 201);
    }

    @Test
    public void putUser() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut("https://gorest.co.in/public/v2/users/7824134?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", "mail@gmail.example"));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPut.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httpPut);
        Assert.assertEquals(response.getCode(), 200);
    }

    @Test
    public void deleteUser() throws IOException {
        HttpUriRequest request = new HttpDelete("https://gorest.co.in/public/v2/users/7824127?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(),204);
    }
}
