
********************************CreateUserTests**************************

This class provides an overview of the CreateUserTests class, including its purpose, assumptions, scenarios tested, and known issues.

Purpose
The CreateUserTests class contains a series of JUnit test cases aimed at testing the functionality of user creation within the context of the GoRestService. These tests cover both successful and unsuccessful scenarios to ensure the robustness and reliability of the user creation process.

Assumptions
The test cases are based on the following assumptions regarding the fields of the user creation model:

1. name: Should only accept characters and should not accept special characters, symbols, numbers, empty strings, or null values.
2. gender: Should only accept gender-specific enumerated values and not random strings, empty strings, or null values.
3. email: Should only accept standard email format, disallow duplicates, and not accept empty strings or null values.
4. status: Should only accept enumerated status values and not random strings, empty strings, or null values.

Scenarios Tested
1. Successful User Creation: Tests the successful creation of a user with valid input data.
2. Missing Name: Tests the case where the name field is empty.
3. Invalid Name: Tests the case where the name contains special characters.
4. Missing Gender: Tests the case where the gender field is empty.
5. Invalid Gender: Tests the case where an invalid gender value is provided.
6. Missing Email: Tests the case where the email field is empty.
7. Invalid Email: Tests the case where an invalid email format is provided. 
8. Duplicate Email: Tests the case where a duplicate email is used. 
9. Missing Status: Tests the case where the status field is empty. 
10. Invalid Status: Tests the case where an invalid status value is provided. 
11. Invalid All Fields: Tests the case where all fields are empty strings. 
12. All Null Fields: Tests the case where all fields are null.

Issues Found during test execution
Issue 1: The test case for an invalid name with special characters currently produces a successful response (HTTP status code 201) instead of the expected error response.
Issue 2: The test case for an invalid gender displays an irrelevant error message ("can't be blank, can be male of female") instead of the expected error message.
Issue 3: The test case for an invalid status displays an irrelevant error message ("can't be blank") instead of the expected error message.


********************************UpdateUserTests**************************

This class provides an overview of the UpdateUserTests class, including its purpose, setup, scenarios tested, and known issues.

Purpose
The UpdateUserTests class contains a series of JUnit test cases aimed at testing the functionality of updating user information within the context of the GoRestService. These tests cover both successful and unsuccessful scenarios to ensure the accuracy and reliability of the update process.

Setup
Before executing any test methods, a user is created using the createUser() method annotated with @BeforeAll. This method utilizes the CreateUserModel to create a user and stores the response and user ID for subsequent tests.

Scenarios Tested
1. Successful Update with All Fields: Tests the successful update of a user with all fields changed.
2. Successful Update with Partial Fields: Tests the successful update of a user with only selected fields changed.
3. Unsuccessful Update with Invalid ID: Tests the case where an update is attempted with a non-existent user ID.
4. Unsuccessful Update with Name as Empty String: Tests the case where the name field is updated with an empty string.
5. Unsuccessful Update with Email as Empty String: Tests the case where the email field is updated with an empty string.
6. Unsuccessful Update with Duplicate Email: Tests the case where the email field is updated with a duplicate email.
7. Unsuccessful Update with Invalid Email: Tests the case where an invalid email format is provided.
8. Unsuccessful Update with Gender as Empty String: Tests the case where the gender field is updated with an empty string.
9. Unsuccessful Update with Status as Empty String: Tests the case where the status field is updated with an empty string.

Known Issues
None at present.


********************************EndToEndUserTests**************************

This class provides an overview of the createEndToEndUserTest() method, including its purpose, steps performed, and expected outcomes.

Purpose
The createEndToEndUserTest() method conducts an end-to-end test scenario for user management functionality within the context of the GoRestService. It covers the entire lifecycle of a user, including creation, retrieval, updating, and deletion, to ensure the completeness and correctness of the user management system.

Steps Performed
1. Create User: A user with initial information is created using the CreateUserModel, and the response is validated to ensure successful creation.
2. Get User: The created user is retrieved using its ID, and the retrieved information is validated to match the initially provided data.
3. Update User: The user's information is updated with new values using the UpdateUserModel, and the response is validated to confirm successful update.
4. Delete User: The user is deleted using its ID, and the response is validated to ensure successful deletion.
5. Get User after Deletion: An attempt is made to retrieve the user again after deletion, and the response is validated to confirm that the user is not found.

Known issues
None at present

Note
This end-to-end test ensures that the user management functionalities, including creation, retrieval, updating, and deletion, are working as expected, providing confidence in the system's reliability and integrity.

********************************FutureEnhancements**************************

1. Logging
2. Reports
3. Integration with schema and error libraries
4. Cucumber BDD
