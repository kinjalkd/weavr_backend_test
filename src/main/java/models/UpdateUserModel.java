package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserModel {

    private String name;
    private String gender;
    private String email;
    private String status;

    public UpdateUserModel() {
    }

        public UpdateUserModel withName(String name) {
        this.name = name;
        return this;
    }

    public UpdateUserModel withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UpdateUserModel withEmail(String email) {
        this.email = email;
        return this;
    }

    public UpdateUserModel withStatus(String status) {
        this.status = status;
        return this;
    }

    public UpdateUserModel build() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.name = this.name;
        updateUserModel.gender = this.gender;
        updateUserModel.email = this.email;
        updateUserModel.status = this.status;
        return updateUserModel;
    }
}

