package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.models.User;
import org.testng.collections.Lists;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

public class TestDataFactory {

    private String apiURL;

    public TestDataFactory(String apiURL) {
        this.apiURL = apiURL;
    }

    private static final List<String> STATUS_LIST = Lists.newArrayList("active", "inactive");

    private static final List<String> GENDER_LIST = Lists.newArrayList("male", "female");

    public int getId() throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpService httpService = new HttpService(apiURL);
        HttpResponse<String> response = httpService.sendGetRequest(0);
        User[] users = mapper.readValue(response.body(), User[].class);
        return users[0].getId();
    }

    public User generateTestUser() throws IOException, URISyntaxException, InterruptedException {
        Random random = new Random();
        int randomStatusListIndex = random.nextInt(STATUS_LIST.size());
        int randomGenderListIndex = random.nextInt(GENDER_LIST.size());
        String email = "TestUser" + System.currentTimeMillis() + "@gmail.com";
        String name = "TestUser" + System.currentTimeMillis();
        User user = new User(name, email, GENDER_LIST.get(randomGenderListIndex), STATUS_LIST.get(randomStatusListIndex));
        return user;
    }
}
