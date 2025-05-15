package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.models.User;
import org.testng.collections.Lists;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TestDataFactory {

    List<String> statusList = Lists.newArrayList("active", "inactive");

    List<String> genderList = Lists.newArrayList("male", "female");

    public int getId() throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        HttpService httpService = new HttpService();
        CloseableHttpResponse response = httpService.sendGetRequest(0);
        User[] users = mapper.readValue(EntityUtils.toString(response.getEntity()), User[].class);
        return users[0].getId();
    }

    public User generateTestUser() throws IOException, ParseException {
        Random random = new Random();
        int randomStatusListIndex = random.nextInt(statusList.size());
        int randomGenderListIndex = random.nextInt(genderList.size());
        String email = String.format("%s@gmail.com", RandomStringUtils.random(10, true, true));
        String name = RandomStringUtils.random(7, true, false);
        User user = new User(getId(), name, email, genderList.get(randomGenderListIndex), statusList.get(randomStatusListIndex));
        return user;
    }
}
