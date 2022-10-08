package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserAuthorizationTest {

    User user;
    UserClient userClient;
    UserCredentials creds;

    @Before
    public void setUp() {
        user = User.getUser();
        userClient = new UserClient();
        userClient.create(user);
        creds = UserCredentials.from(user);
    }

    @After
    public void tearDown() {
        userClient.tearDown(creds);
    }

    @Test
    @DisplayName("Возможен логин под существующим пользователем")
    @Description("Проверка успешного логина пользователя при передаче в теле запроса существующих значений: email, password, name")
    public void userCorrectAuthorization() {
        boolean isOk = userClient.login(creds)
                .statusCode(200)
                .extract()
                .path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Невозможна авторизация под некорректным логином")
    @Description("Проверка невозможности авторизации пользователя при передаче в теле запроса некорректного значения name")
    public void userAuthWithIncorrectLogin() {
        UserCredentials newCreds = UserCredentials.withoutIncorrectLoginFrom(user);
        boolean isOk = userClient.login(newCreds)
                .statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOk);
    }

    @Test
    @DisplayName("Невозможна авторизация под некорректным паролем")
    @Description("Проверка невозможности авторизации пользователя при передаче в теле запроса некорректного значения password")
    public void userAuthWithIncorrectPassword() {
        UserCredentials newCreds = UserCredentials.withoutIncorrectPasswordFrom(user);
        boolean isOk = userClient.login(newCreds)
                .statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOk);
    }
}
