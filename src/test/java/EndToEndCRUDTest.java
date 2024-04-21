import io.restassured.response.Response;
import models.CreateUserModel;
import models.UpdateUserModel;
import org.junit.jupiter.api.Test;
import services.GoRestService;
import utils.TestUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class EndToEndCRUDTest extends TestUtils {

    /**
     *  Within this class I have tested the end to end functionality using the CRUD operation
     *  Create, Read, Update and Delete
     */

    @Test
    public void createEndToEndUserTest() {

        Response response;
        int id;

        //Create User
        final CreateUserModel createUserModel = new CreateUserModel("Original User", "male",
                TestUtils.generateRandomString() + "@test.com", "active");
        response = GoRestService.createUser(createUserModel);
        validateResponseStatusCode(response, SC_CREATED);
        id = (int) getResponsePath(response, "data.id");

        //Get User
        response = GoRestService.getUserWithId(id);
        validateResponseStatusCode(response, SC_OK);
        response.then().body("data.id", equalTo(id));
        response.then().body("data.name", equalTo(createUserModel.getName()));
        response.then().body("data.gender", equalTo(createUserModel.getGender()));
        response.then().body("data.email", equalTo(createUserModel.getEmail()));
        response.then().body("data.status", equalTo(createUserModel.getStatus()));

        //Update User
        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("Updated User")
                .withEmail(TestUtils.generateRandomString() + "updated@test.com")
                .withGender("female")
                .withStatus("inactive")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_OK);
        response.then().body("data.name", equalTo(updateUserModel.getName()));
        response.then().body("data.email", equalTo(updateUserModel.getEmail()));
        response.then().body("data.gender", equalTo(updateUserModel.getGender()));
        response.then().body("data.status", equalTo(updateUserModel.getStatus()));

        //Delete User
        response = GoRestService.deleteUserWithId(id);
        validateResponseStatusCode(response, SC_NO_CONTENT);

        //Get User after Deletion
        response = GoRestService.getUserWithId(id);
        validateResponseStatusCode(response, SC_NOT_FOUND);
        response.then().body("data.message", equalTo("Resource not found"));
    }
}


