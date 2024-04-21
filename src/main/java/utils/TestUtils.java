package utils;

import io.restassured.response.Response;
import java.util.Random;

public class TestUtils {

    //Method to generate random string for unique email
    public static String generateRandomString() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    //Method to validate response status code
    public static void validateResponseStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        if (actualStatusCode != expectedStatusCode) {
            throw new AssertionError("Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        }
    }

    //Method to get value of the given path from the response object
    public static Object getResponsePath(Response response, String path) {
       return response.path(path);
    }
}
