package org.example;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
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

public class CommentsTest {
    @Test
    public void getComments() throws ClientProtocolException, IOException {
        HttpUriRequest request=new HttpGet("https://gorest.co.in/public/v2/comments");
        HttpResponse httpResponse= HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(),200);
    }

    @Test
    public void getComment() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet("https://gorest.co.in/public/v2/comments/147013");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(), 200);
    }

    @Test
    public void postComment() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://gorest.co.in/public/v2/comments?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("post_id", "203565"));
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", "mail@gmail.example"));
        params.add(new BasicNameValuePair("body", "comment"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httpPost);
        Assert.assertEquals(response.getCode(), 201);
    }

    @Test
    public void putComment() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut("https://gorest.co.in/public/v2/comments/147013?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("post_id", "203565"));
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("email", "mail@gmail.example"));
        params.add(new BasicNameValuePair("body", "comment"));
        httpPut.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httpPut);
        Assert.assertEquals(response.getCode(), 200);
    }

    @Test
    public void deleteComment() throws IOException {
        HttpUriRequest request = new HttpDelete("https://gorest.co.in/public/v2/comments/146982?access-token=5d034ab1ba63642fd87b6ff1133eb5fbadad18da23c7c44bbe1f857e25b733b7");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getCode(),204);

    }
}
