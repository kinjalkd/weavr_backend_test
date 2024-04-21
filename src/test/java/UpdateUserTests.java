import io.restassured.response.Response;
import models.CreateUserModel;
import models.UpdateUserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.GoRestService;
import utils.TestUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTests extends TestUtils {

    static Response response;
    static int id;

    //Static method to create user before all other methods
    @BeforeAll
    public static void createUser() {

        final CreateUserModel createUserModel = new CreateUserModel("Anthony Paloma", "male",
                TestUtils.generateRandomString() + "@test.com", "active");
        response = GoRestService.createUser(createUserModel);
        validateResponseStatusCode(response, SC_CREATED);
        id = (int) getResponsePath(response, "data.id");
    }

    //Scenario: Successful update
    @Test
    public void testSuccessfulUpdateUserAllFields() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("Tina Paloma")
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
    }

    //Scenario: Successful udpate using only selected fields
    @Test
    public void testSuccessfulUpdateUserPartialFields() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("John Paloma")
                .withEmail(TestUtils.generateRandomString() + "updated@test.com")
                .withGender("male")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_OK);
        response.then().body("data.name", equalTo(updateUserModel.getName()));
        response.then().body("data.email", equalTo(updateUserModel.getEmail()));
        response.then().body("data.gender", equalTo(updateUserModel.getGender()));
    }

    //Scenario : Unsuccessful update with non-existent id
    @Test
    public void testUnsuccessfulUpdateWithInvalidId() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withEmail(TestUtils.generateRandomString() + "updated@test.com")
                .build();
        response = GoRestService.updateUser(updateUserModel, 12345);
        validateResponseStatusCode(response, SC_NOT_FOUND);
        response.then().body("data.message", equalTo("Resource not found"));
    }

    //Scenario: Unsuccessful test case with name as empty string
    @Test
    public void testUnsuccessfulUpdateWithNameAsEmptyString() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("name"));
        response.then().body("data[0].message", equalTo("can't be blank"));
    }

    //Scenario: Unsuccessful test case with email as empty string
    @Test
    public void testUnsuccessfulUpdateWithEmailAsEmptyString() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("Anton Paloma")
                .withEmail("")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("email"));
        response.then().body("data[0].message", equalTo("can't be blank"));
    }

    //Scenario: Unsuccessful update with duplicate email
    @Test
    public void testUnsuccessfulUpdateWithDuplicateEmail() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withEmail("duplicate_email@test.com")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("email"));
        response.then().body("data[0].message", equalTo("has already been taken"));
    }

    //Scenario: Unsuccessful update with Invalid email (invalid_email)
    @Test
    public void testUnsuccessfulUpdateWithInvalidEmail() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withEmail("invalid_email")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("email"));
        response.then().body("data[0].message", equalTo("is invalid"));
    }

    //Scenario: Unsuccessful update with gender as an empty string
    @Test
    public void testUnsuccessfulUpdateWithGenderAsEmptyString() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("Anton Paloma")
                .withEmail(TestUtils.generateRandomString() + "updated@test.com")
                .withGender("")
                .withStatus("inactive")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("gender"));
        response.then().body("data[0].message", equalTo("can't be blank, can be male of female"));
    }

    //Scenario: Unsuccessful update with status as an empty string
    @Test
    public void testUnsuccessfulUpdateWithStatusAsEmptyString() {

        final UpdateUserModel updateUserModel = new UpdateUserModel()
                .withName("Anton Paloma")
                .withEmail(TestUtils.generateRandomString() + "updated@test.com")
                .withGender("male")
                .withStatus("")
                .build();
        response = GoRestService.updateUser(updateUserModel, id);
        validateResponseStatusCode(response, SC_UNPROCESSABLE_ENTITY);
        response.then().body("data[0].field", equalTo("status"));
        response.then().body("data[0].message", equalTo("can't be blank"));
    }
}


