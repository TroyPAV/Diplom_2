package user;

import config.Config;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL);
    }

    protected RequestSpecification getSpecWithAuth(String userToken) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", userToken)
                .baseUri(Config.BASE_URL);
    }

}
