package org.example.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private int id;

    private String name;

    private String email;

    private String gender;

    private String status;

}
