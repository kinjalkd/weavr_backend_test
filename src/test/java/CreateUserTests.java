import models.CreateUserModel;
import org.junit.jupiter.api.Test;
import services.GoRestService;
import utils.TestUtils;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.hamcrest.CoreMatchers.*;

public class CreateUserTests extends TestUtils {

    /**
     * Assumptions:
     * 1. name - field should accept only characters and does not accept special character, symbols, numbers, empty strings, null etc
     * 2. gender - field should accept only gender-specific enumerated values and no random strings, empty strings or null
     * 3. email - field should accept only standard email format, no duplicates allowed, no empty strings or null
     * 4. status - field should accept only enumerated status values and no random strings, empty strings or null
     */

    //Scenario : Successful Test Case
    @Test
    public void testSuccessfulUserCreation() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "Male",
                TestUtils.generateRandomString() + "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_CREATED)
                .body("data.id", notNullValue())
                .body("data.name", equalTo(createUserModel.getName()));
    }

    //Scenario : Unsuccessful case where name is an empty string
    @Test
    public void testMissingName() {

        final CreateUserModel createUserModel = new CreateUserModel("", "Male",
                TestUtils.generateRandomString() + "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("name"))
                .body("data[0].message", equalTo("can't be blank"));
    }

    //Scenario : Unsuccessful case where name is invalid (special characters)
    //ISSUE 1- Invalid name with special characters creates successful response 201
    @Test
    public void testInvalidName() {

        final CreateUserModel createUserModel = new CreateUserModel("£$%^&*", "Male",
                TestUtils.generateRandomString() + "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("name"))
                .body("data[0].message", equalTo("is invalid"));
    }

    //Scenario : Unsuccessful case where gender is an empty string
    @Test
    public void testMissingGender() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "",
                TestUtils.generateRandomString() + "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("gender"))
                .body("data[0].message", equalTo("can't be blank, can be male of female"));
    }

    //Scenario : Unsuccessful case where gender is invalid (xyz)
    //ISSUE 2 - Invalid gender eg. 'xyz' shows irrelevant error message "can't be blank, can be male of female"
    @Test
    public void testInvalidGender() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "xyz",
                TestUtils.generateRandomString() + "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("gender"))
                .body("data[0].message", equalTo("is invalid"));
    }

    //Scenario : Unsuccessful case where email is an empty string
    @Test
    public void testMissingEmail() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "male",
                "", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", equalTo("can't be blank"));
    }

    //Scenario : Unsuccessful case where email is invalid "@test.com"
    @Test
    public void testInvalidEmail() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "male",
                "@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", equalTo("is invalid"));
    }

    //Scenario : Unsuccessful case where duplicate email is used
    @Test
    public void testDuplicateEmail() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "male",
                "abc@15ce.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", equalTo("has already been taken"));
    }

    //Scenario : Unsuccessful case where status is an empty field
    @Test
    public void testMissingStatus() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "male",
                TestUtils.generateRandomString() + "@test.com", "");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("status"))
                .body("data[0].message", equalTo("can't be blank"));
    }

    //Scenario : Unsuccessful case where status is invalid (abc)
    //ISSUE 3 - Invalid status eg. 'abc' shows irrelevant error message "can't be blank"
    @Test
    public void testInvalidStatus() {

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "male",
                TestUtils.generateRandomString() + "@test.com", "abc");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("status"))
                .body("data[0].message", equalTo("is invalid"));
    }

    //Scenario : Unsuccessful case where all fields are empty spaces
    @Test
    public void testInvalidAllFields() {
        final CreateUserModel createUserModel = new CreateUserModel("", "", "", "");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY);
    }

    //Scenario: Unsuccessful case where all fields are null
    @Test
    public void testAllNullFields() {

        final CreateUserModel createUserModel = new CreateUserModel(null, null,
                null, null);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY);
    }
}


