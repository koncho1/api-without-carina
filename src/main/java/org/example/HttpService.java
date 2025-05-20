package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.HttpMethodType;
import org.example.models.User;

import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static org.example.enums.HttpMethodType.*;

public class HttpService {

    private String token;

    private HttpClient client;

    private String apiURL;

    public HttpService(String apiURL) {
        this.apiURL = apiURL;
        client = HttpClient.newBuilder().build();
    }

    public HttpService(String token, String apiURL) {
        this.token = token;
        this.apiURL = apiURL;
        client = HttpClient.newBuilder().build();
    }

    public HttpResponse<String> sendGetRequest(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = createRequest(id, GET, "");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPostRequest(User user) throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = createRequest(user.getId(), POST, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPostRequestWithInvalidEmail() throws IOException, URISyntaxException, InterruptedException {
        String body = "{\"name\": \"John Doe\",\"mail\": \"ddsadfasadsadsada@mail.com\",\"gender\": \"male\",\"status\":\"active\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiURL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPutRequest(User user) throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = createRequest(user.getId(), PUT, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPatchRequest(User user) throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = createRequest(user.getId(), PATCH, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendDeleteRequest(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = createRequest(id, DELETE, "");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendDeleteRequestWithoutToken(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiURL + "/" + id))
                .DELETE()
                .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpRequest createRequest(int userId, HttpMethodType methodType, String body) throws URISyntaxException {
        String url = apiURL;
        if (userId != 0) {
            url += "/" + userId;
        }
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(new URI(url));
        switch (methodType) {
            case GET:
                request.GET()
                        .headers("Accept", "application/json", "Content-type", "application/json");
                break;
            case PATCH:
                request.PUT(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json");
                break;
            case PUT:
                request.PUT(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json");
                break;
            case POST:
                request.POST(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json");
                break;
            case DELETE:
                request.DELETE()
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json");
                break;
            default:
                throw new UnsupportedOperationException("This method is not supported");
        }
        return request.build();
    }


}
