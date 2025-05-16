package org.example;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphQLService {

    private String token;

    private HttpClient httpClient;

    public GraphQLService(String token) {
        this.token = token;
        httpClient = HttpClients.createDefault();
    }

    private static final String API_URL = "https://gorest.co.in/public/v2/graphql";

    public ClassicHttpResponse graphqlGetUserById(int id) throws IOException {
        String query = String.format("query User {\n" +
                "    user(id: %s) {\n" +
                "        email\n" +
                "        gender\n" +
                "        id\n" +
                "        name\n" +
                "        status\n" +
                "    }\n" +
                "}", id);
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("query", query));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse graphqlGetUserCount() throws IOException {
        String query = "query Users {\n" +
                "    users {\n" +
                "        totalCount\n" +
                "    }\n" +
                "}";
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("query", query));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse graphqlDeleteUser(int id) throws IOException {
        String query = String.format("mutation DeleteUser {\n" +
                "    deleteUser(input: { id: %s }) {\n" +
                "        user {\n" +
                "            email\n" +
                "            gender\n" +
                "            id\n" +
                "            name\n" +
                "        }\n" +
                "    }\n" +
                "}", id);
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("query", query));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse graphqlCreateUser(User user) throws IOException {
        String query = String.format("mutation CreateUser {\n" +
                "    createUser(\n" +
                "        input: {\n" +
                "            name: \"%s\"\n" +
                "            email: \"%s\"\n" +
                "            gender: \"%s\"\n" +
                "            status: \"%s\"\n" +
                "        }\n" +
                "    ) {\n" +
                "        user {\n" +
                "            email\n" +
                "            gender\n" +
                "            id\n" +
                "            name\n" +
                "            status\n" +
                "        }\n" +
                "    }\n" +
                "}", user.getName(), user.getEmail(), user.getGender(), user.getStatus());
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("query", query));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpPost);
        return response;
    }

    public ClassicHttpResponse graphqlUpdateUser(User user) throws IOException {
        String query = String.format("mutation UpdateUser {\n" +
                "    updateUser(\n" +
                "        input: {\n" +
                "            id: %s\n" +
                "            email: \"%s\"\n" +
                "            name: \"%s\"\n" +
                "            gender: \"%s\"\n" +
                "            status: \"%s\"\n" +
                "        }\n" +
                "    ) {\n" +
                "        user {\n" +
                "            email\n" +
                "            gender\n" +
                "            id\n" +
                "            name\n" +
                "            status\n" +
                "        }\n" +
                "    }\n" +
                "}\n", user.getId(), user.getEmail(), user.getName(), user.getGender(), user.getStatus());
        HttpPost httpPost = new HttpPost(API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("query", query));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Header accept = new BasicHeader(HttpHeaders.ACCEPT, "application/json");
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeaders(auth, accept, contentType);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpPost);
        return response;
    }
}
