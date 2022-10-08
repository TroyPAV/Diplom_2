package order;

import io.restassured.response.ValidatableResponse;
import user.BaseClient;

public class OrderClient extends BaseClient {

    private final String ORDER = "orders";

    public ValidatableResponse createWithAuth(String userToken, Order order) {
        return getSpecWithAuth(userToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse createWithoutAuth(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getOrderWithAuth(String userToken) {
        return getSpecWithAuth(userToken)
                .get(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getOrderWithoutAuth() {
        return getSpec()
                .get(ORDER)
                .then().log().all();
    }
}
