package org.example;

import org.example.enums.GraphQLTemplateName;
import org.example.models.User;

import java.net.http.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.example.enums.GraphQLTemplateName.*;

public class GraphQLService {

    private static final String TEMPLATE_FOLDER_PATH = "src/main/java/org/example/resources/";

    private String token;

    private String apiURL;

    private HttpClient httpClient;

    public GraphQLService(String token, String apiURL) {
        this.token = token;
        this.apiURL = apiURL;
        httpClient = HttpClient.newBuilder().build();
    }

    public HttpResponse<String> getUserById(int id) throws IOException, URISyntaxException, InterruptedException {
        String query = String.format(Files.readString(Paths.get(TEMPLATE_FOLDER_PATH + GET_USER_BY_ID.getTemplateName())), id);
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> getUserCount() throws IOException, URISyntaxException, InterruptedException {
        String query = Files.readString(Paths.get(TEMPLATE_FOLDER_PATH + GET_USER_COUNT.getTemplateName()));
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> deleteUser(int id) throws IOException, URISyntaxException, InterruptedException {
        String query = String.format(Files.readString(Paths.get(TEMPLATE_FOLDER_PATH + DELETE_USER.getTemplateName())), id);
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> createUser(User user) throws IOException, URISyntaxException, InterruptedException {
        String query = String.format(Files.readString(Paths.get(TEMPLATE_FOLDER_PATH + CREATE_USER.getTemplateName())), user.getName(), user.getEmail(), user.getGender(), user.getStatus());
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> updateUser(User user) throws IOException, URISyntaxException, InterruptedException {
        String query = String.format(Files.readString(Paths.get(TEMPLATE_FOLDER_PATH + UPDATE_USER.getTemplateName())), user.getId(), user.getEmail(), user.getName(), user.getGender(), user.getStatus());
        HttpRequest request = createRequest(query);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpRequest createRequest(String body) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiURL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                .build();
        return request;
    }
}
