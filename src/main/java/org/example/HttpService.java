package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpHeaders;
import org.example.models.User;

import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class HttpService {

    private static final String PUT_METHOD = "put";

    private static final String GET_METHOD = "get";

    private static final String POST_METHOD = "post";

    private static final String PATCH_METHOD = "patch";

    private static final String DELETE_METHOD = "delete";

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
        HttpRequest request = createRequest(id, GET_METHOD, "");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPostRequest(User user) throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = createRequest(user.getId(), POST_METHOD, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPostRequestWithIncorrectParams() throws IOException, URISyntaxException, InterruptedException {
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
        HttpRequest request = createRequest(user.getId(), PUT_METHOD, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendPatchRequest(User user) throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = createRequest(user.getId(), PATCH_METHOD, mapper.writeValueAsString(user));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> sendDeleteRequest(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = createRequest(id, DELETE_METHOD, "");
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

    private HttpRequest createRequest(int userId, String methodType, String body) throws URISyntaxException {
        HttpRequest request;
        String url = apiURL;
        if (userId != 0) {
            url += "/" + userId;
        }
        switch (methodType) {
            case GET_METHOD:
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .headers("Accept", "application/json", "Content-type", "application/json")
                        .build();
                break;
            case PATCH_METHOD:
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                        .build();
                break;
            case PUT_METHOD:
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                        .build();
                break;
            case POST_METHOD:
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                        .build();
                break;
            case DELETE_METHOD:
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .DELETE()
                        .headers("Authorization", "Bearer " + token, "Accept", "application/json", "Content-type", "application/json")
                        .build();
                break;
            default:
                throw new UnsupportedOperationException("This method is not supported");
        }
        return request;
    }


}
