package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.BaseClient;

public class OrderClient extends BaseClient {

    private final String ORDER = "orders";

    @Step("Запрос на создание заказа с передачей токена авторизации зарегистрированного пользователя (POST на /api/orders)")
    public ValidatableResponse createOrderWithAuth(String userToken, Order order) {
        return getSpecWithAuth(userToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    @Step("Запрос на создание заказа без передачи токена авторизации зарегистрированного пользователя POST на /api/orders")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    @Step("Запрос на получение заказов с передачей токена авторизации зарегистрированного пользователя (GET на /api/orders)")
    public ValidatableResponse getOrderWithAuth(String userToken) {
        return getSpecWithAuth(userToken)
                .get(ORDER)
                .then().log().all();
    }

    @Step("Запрос на получение заказов без передачи токена авторизации зарегистрированного пользователя (GET на /api/orders)")
    public ValidatableResponse getOrderWithoutAuth() {
        return getSpec()
                .get(ORDER)
                .then().log().all();
    }
}
