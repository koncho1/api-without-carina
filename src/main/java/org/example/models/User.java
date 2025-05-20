package org.example.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    public User(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    private int id;

    private String name;

    private String email;

    private String gender;

    private String status;

}
