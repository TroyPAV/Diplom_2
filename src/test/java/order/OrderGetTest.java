package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;

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
        userClient.create(user);
        creds = UserCredentials.from(user);
        userToken = userClient.getToken(creds);
        orderClient = new OrderClient();
        order = Order.getIngredients();
        orderClient.createWithAuth(userToken, order);
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Можно получить заказы авторизованниго пользователя")
    @Description("Проверка получения заказов конкретного пользователя при передаче токена авторизации")
    public void test1() {
        boolean isOk = orderClient.getOrderWithAuth(userToken)
                .statusCode(200)
                .extract()
                .path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Невозможно получить заказы неавторизованого пользователя")
    @Description("Проверка невозможности получения заказов пользователя, если не передовать токен авторизации")
    public void test2() {
        boolean isOk = orderClient.getOrderWithoutAuth()
                .statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOk);
    }
}
