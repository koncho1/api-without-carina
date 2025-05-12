package org.example;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;

import java.io.IOException;

public class GetMethod{

    public void getUsers() throws ClientProtocolException, IOException {
        HttpUriRequest request=new HttpGet("https://gorest.co.in/public/v2/users");
        HttpResponse httpResponse= HttpClientBuilder.create().build().execute(request);
    }
}
