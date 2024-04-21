package services;

import io.restassured.response.Response;
import models.CreateUserModel;
import models.UpdateUserModel;


public class GoRestService extends BaseService {

    public static Response createUser(final CreateUserModel createUserModel){
        Response response = defaultRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/users");
        response.prettyPeek();
        return response;
    }

    public static Response getUserWithId(int id){
        Response response = defaultRequestSpecification()
                .when()
                .get(String.format("/public/v1/users/%s", id));
        return response;
    }

    public static Response updateUser(final UpdateUserModel updateUserModel, int id){
        Response response = defaultRequestSpecification()
                .body(updateUserModel)
                .log().all()
                .when()
                .put(String.format("/public/v1/users/%s", id));
        return response;
    }

    public static Response deleteUserWithId(int id){
        Response response = defaultRequestSpecification()
                .when()
                .delete(String.format("/public/v1/users/%s", id));
        return response;
    }
}
