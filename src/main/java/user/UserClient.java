package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseClient{

    private final String ROOT = "auth/";
    private final String REGISTER = ROOT + "register";
    private final String LOGIN = ROOT + "login";
    private final String USER = ROOT + "user";
    private String userToken;

    @Step("Запрос на создание нового пользователя (POST на /api/auth/register)")
    public ValidatableResponse createUser(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Запрос на авторизацию пользователя (POST на /api/auth/login)")
    public ValidatableResponse login(UserCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Запрос на получение accessToken зарегистрированного пользователя")
    public String getToken(UserCredentials creds){
        return login(creds)
                .extract()
                .path("accessToken");
    }

    @Step("Запрос на удаление зарегистрированного пользователя (DELETE на /api/auth/user)")
    public void deleteUser(String userToken) {
        getSpecWithAuth(userToken)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Запрос с передачей токена авторизации на изменение данных зарегистрированного пользователя (PATCH на /api/auth/user)")
    public ValidatableResponse changeUser(String userToken, UserCredentials creds) {
        return getSpecWithAuth(userToken)
                .body(creds)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Запрос без передачи токена авторизации на изменение данных зарегистрированного пользователя (PATCH на /api/auth/user)")
    public ValidatableResponse changeUserWithoutToken(UserCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Удаление созданного пользователя")
    public void tearDown(UserCredentials creds) {
        userToken = getToken(creds);
        deleteUser(userToken);
    }
}
