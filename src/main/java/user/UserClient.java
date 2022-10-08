package user;

import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseClient{

    private final String ROOT = "auth/";
    private final String REGISTER = ROOT + "register";
    private final String LOGIN = ROOT + "login";
    private final String USER = ROOT + "user";
    private String userToken;

    public ValidatableResponse create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    public ValidatableResponse login(UserCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public String getToken(UserCredentials creds){
        return login(creds)
                .extract()
                .path("accessToken");
    }

    public void delete(String userToken) {
        getSpecWithAuth(userToken)
                .when()
                .delete(USER)
                .then().log().all();
    }

    public ValidatableResponse change(String userToken, UserCredentials creds) {
        return getSpecWithAuth(userToken)
                .body(creds)
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeWithoutToken(UserCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .patch(USER)
                .then().log().all();
    }

    public void tearDown(UserCredentials creds) {
        userToken = getToken(creds);
        delete(userToken);
    }
}
