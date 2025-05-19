package org.example;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.models.User;

import java.net.http.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GraphQLService {

    private String token;

    private static final String API_URL = "https://gorest.co.in/public/v2/graphql";

    private String apiURL;

    private HttpClient httpClient;

    public GraphQLService(String token, String apiURL) {
        this.token = token;
        this.apiURL = apiURL;
        httpClient = HttpClient.newBuilder().build();
    }

    public HttpResponse<String> graphqlGetUserById(int id) throws IOException, URISyntaxException, InterruptedException {
        String query = String.format("query User {\n" +
                "    user(id: %s) {\n" +
                "        email\n" +
                "        gender\n" +
                "        id\n" +
                "        name\n" +
                "        status\n" +
                "    }\n" +
                "}", id);
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> graphqlGetUserCount() throws IOException, URISyntaxException, InterruptedException {
        String query = "query Users {\n" +
                "    users {\n" +
                "        totalCount\n" +
                "    }\n" +
                "}";
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> graphqlDeleteUser(int id) throws IOException, URISyntaxException, InterruptedException {
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
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> graphqlCreateUser(User user) throws IOException, URISyntaxException, InterruptedException {
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
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> graphqlUpdateUser(User user) throws IOException, URISyntaxException, InterruptedException {
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
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpRequest createRequest(String body) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                .build();
        return request;
    }
}
