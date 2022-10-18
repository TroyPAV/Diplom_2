package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;
import static org.apache.http.HttpStatus.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderGetTest {

    User user;
    Order order;
    UserClient userClient;
    OrderClient orderClient;
    UserCredentials creds;
    private String userToken;

    @Before
    public void setUp() {
        user = User.getUser();
        userClient = new UserClient();
        userClient.createUser(user);
        creds = UserCredentials.from(user);
        userToken = userClient.getToken(creds);
        orderClient = new OrderClient();
        order = Order.getIngredients();
        orderClient.createOrderWithAuth(userToken, order);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(userToken);
    }

    @Test
    @DisplayName("Можно получить заказы авторизованниго пользователя")
    @Description("Проверка получения заказов конкретного пользователя при передаче токена авторизации")
    public void getOrderAuthorizedUser() {
        boolean isOk = orderClient.getOrderWithAuth(userToken)
                .statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Невозможно получить заказы неавторизованого пользователя")
    @Description("Проверка невозможности получения заказов пользователя, если не передовать токен авторизации")
    public void getOrderUnauthorizedUser() {
        boolean isOk = orderClient.getOrderWithoutAuth()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(isOk);
    }
}
