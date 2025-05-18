package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpService {

    private static final String API_URL = "https://gorest.co.in/public/v2/users";

    private String token;

    private HttpClient client;

    public HttpService() {
        client = HttpClients.createDefault();
    }

    public HttpService(String token) {
        this.token = token;
        client = HttpClients.createDefault();
    }

    public ClassicHttpResponse sendGetRequest(int id) throws IOException {
        String url = API_URL;
        if (id != 0) {
            url += "/" + id;
        }
        HttpUriRequest request = new HttpGet(url);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        request.setHeader(accept);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        return response;
    }

    public ClassicHttpResponse sendPostRequest(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpPost httpPost = new HttpPost(API_URL);
        String json = mapper.writeValueAsString(user);
        HttpEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse sendPostRequestWithIncorrectParams() throws IOException {
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", "John Doe"));
        params.add(new BasicNameValuePair("mail", String.format("%s@gmail.com", RandomStringUtils.random(10, true, true))));
        params.add(new BasicNameValuePair("gender", "male"));
        params.add(new BasicNameValuePair("status", "active"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse sendPutRequest(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String url = API_URL;
        if (user.getId() != 0) {
            url += "/" + user.getId();
        }
        HttpPut httpPut = new HttpPut(url);
        String json = mapper.writeValueAsString(user);
        HttpEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPut.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(httpPut);
        return response;
    }

    public ClassicHttpResponse sendPatchRequest(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String url = API_URL;
        if (user.getId() != 0) {
            url += "/" + user.getId();
        }
        HttpPatch httpPatch = new HttpPatch(url);
        String json = mapper.writeValueAsString(user);
        HttpEntity entity = new StringEntity(json);
        httpPatch.setEntity(entity);
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPatch.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(httpPatch);
        return response;
    }

    public ClassicHttpResponse sendDeleteRequest(int id) throws IOException {
        HttpUriRequest request = new HttpDelete("https://gorest.co.in/public/v2/users/" + id);
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(header);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        return response;
    }

    public ClassicHttpResponse sendDeleteRequestWithoutToken(int id) throws IOException {
        HttpUriRequest request = new HttpDelete("https://gorest.co.in/public/v2/users/" + id);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        return response;
    }


}
