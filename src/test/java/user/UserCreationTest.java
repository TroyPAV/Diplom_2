package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static org.junit.Assert.*;

public class UserCreationTest {

    User user;
    UserClient userClient;
    UserCredentials creds;

    @Before
    public void setUp() {
        user = User.getUser();
        userClient = new UserClient();
        creds = UserCredentials.from(user);
    }

    @Test
    @DisplayName("Можно создать уникального пользователя")
    @Description("Проверка создания нового пользователя при передаче в теле запроса валидных значений: email, password, name.")
    public void userIsCreated() {
        boolean isOk = userClient.createUser(user)
                .statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isOk);
        userClient.tearDown(creds);
    }

    @Test
    @DisplayName("Невозможно создать двух одиноковых пользователей")
    @Description("Проверка невозможности создания двух пользователей с одинаковыми значениями полей: email, password, name.")
    public void checkTwoIdenticalUsersCreation() {
        userClient.createUser(user);
        boolean isOk = userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isOk);
        userClient.tearDown(creds);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без почты")
    @Description("Проверка невозможности создания пользователя при запросе на создание пользователя без поля email")
    public void checkUserCreationWithoutEmail() {
        user = User.getUserWithoutEmail();
        String message = userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без пароля")
    @Description("Проверка невозможности создания пользователя при запросе на создание пользователя без поля password")
    public void checkUserCreationWithoutPassword() {
        user = User.getUserWithoutPassword();
        String message = userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без имени")
    @Description("Проверка невозможности создания пользователя при запросе на создание пользователя без поля neme")
    public void checkUserCreationWithoutName() {
        user = User.getUserWithoutName();
        String message = userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");
        assertEquals("Email, password and name are required fields", message);
    }
}
