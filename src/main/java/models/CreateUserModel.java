package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserModel {

    private String name;
    private String gender;
    private String email;
    private String status;

    public CreateUserModel(String name,
                           String gender,
                           String email,
                           String status) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }
}
