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

public class OrderCreationTest {

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
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Можно создать заказ с ингридиентами при успешной авторизации")
    @Description("Проверка создания нового заказа при передаче токена и валидных хешей ингредиентов")
    public void checkOrderCreateWithAuthAndIngredients() {
        order = Order.getIngredients();
        boolean isOk = orderClient.createWithAuth(userToken, order)
                .statusCode(200)
                .extract()
                .path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Невозможно создать заказ без ингридиентов при успешной авторизации")
    @Description("Проверка невозможности создания нового заказа при передаче токена без ингредиентов")
    public void checkOrderCreateWithAuthAndWithoutIngredients() {
        order = Order.withoutIngredients();
        boolean isOk = orderClient.createWithAuth(userToken, order)
                .statusCode(400)
                .extract()
                .path("success");
        assertFalse(isOk);
    }

    @Test
    @DisplayName("Невозможно создать заказ с ингридиентами без авторизации")
    @Description("Проверка невозможности создания нового заказа при передаче валидных хешей ингредиентов без авторизации")
    public void checkOrderCreateWithoutAuthAndWithIngredients() {
        order = Order.getIngredients();
        boolean isOk = orderClient.createWithoutAuth(order)
                .statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOk);
    }

    @Test
    @DisplayName("Невозможно создать заказ без ингридиентами и без авторизации")
    @Description("Проверка невозможности создания нового заказа без ингредиентов и без передаче токена авторизации")
    public void checkOrderCreateWithoutAuthAndWithoutIngredients() {
        order = Order.withoutIngredients();
        boolean isOk = orderClient.createWithoutAuth(order)
                .statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOk);
    }

    @Test
    @DisplayName("Internal Server Error при невалидном хеше ингредиента")
    @Description("Проверка возвращения 500 Internal Server Error при передаче в теле запроса невалидных хешей ингридиентов.")
    public void checkInvalidHashIngredients() {
        order = Order.getIncorrectIngredients();
        orderClient.createWithAuth(userToken, order)
                .statusCode(500);
    }
}