package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserChangeTest {

    User user;
    UserClient userClient;
    UserCredentials creds;
    private String userToken;

    @Before
    public void setUp() {
        user = User.getUser();
        userClient = new UserClient();
        userClient.create(user);
        creds = UserCredentials.from(user);
        userToken = userClient.getToken(creds);
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Можно поменять email авторизованного пользователя")
    @Description("Проверка измениения поля email авторизованного пользователя при передаче токена и нового email в теле запроса.")
    public void checkChangeUserEmailWithAuth() {
        UserCredentials newCreds = UserCredentials.changedEmailFrom(user);
        boolean isOk = userClient.change(userToken, newCreds)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Можно поменять имя авторизованного пользователя")
    @Description("Проверка измениения поля name авторизованного пользователя при передаче токена и нового name в теле запроса.")
    public void checkChangeUserNameWithAuth() {
        UserCredentials newCreds = UserCredentials.changedNameFrom(user);
        boolean isOk = userClient.change(userToken, newCreds)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Сообщение об ошибке при изменении email неавторизованного пользователя")
    @Description("Проверка возвращения message с сообщением об ошибке в теле ответа при запросе на изменение email без авторизвции.")
    public void checkChangeUserEmailWithoutAuth() {
        UserCredentials newCreds = UserCredentials.changedEmailFrom(user);
        String message = userClient.changeWithoutToken(newCreds)
                .statusCode(401)
                .extract().path("message");
        assertEquals("You should be authorised", message);
    }

    @Test
    @DisplayName("Сообщение об ошибке при изменении имени неавторизованного пользователя")
    @Description("Проверка возвращения message с сообщением об ошибке в теле ответа при запросе на изменение name без авторизвции.")
    public void checkChangeUserNameWithoutAuth() {
        UserCredentials newCreds = UserCredentials.changedNameFrom(user);
        String message = userClient.changeWithoutToken(newCreds)
                .statusCode(401)
                .extract().path("message");
        assertEquals("You should be authorised", message);
    }
}
