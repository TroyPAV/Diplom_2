package user;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class User {

    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getUser() {
        return new User(
                RandomStringUtils.randomAlphabetic(10) + "@example.com",
                "pass123",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutEmail() {
        return new User(
                "",
                "pass123",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutPassword() {
        return new User(
                RandomStringUtils.randomAlphabetic(10) + "@example.com",
                "",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutName() {
        return new User(
                RandomStringUtils.randomAlphabetic(10) + "@example.com",
                "pass123",
                ""
        );
    }
}
