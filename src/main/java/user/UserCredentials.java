package user;

import lombok.Data;

@Data
public class UserCredentials {

    private String email;
    private String password;
    private String name;

    public UserCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword(), user.getName());
    }

    public static UserCredentials withoutIncorrectLoginFrom(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword(), user.getName() + "123");
    }

    public static UserCredentials withoutIncorrectPasswordFrom(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword() + "asd", user.getName());
    }

    public static UserCredentials changedEmailFrom(User user) {
        return new UserCredentials("Parker" + user.getEmail(), user.getPassword(), user.getName());
    }

    public static UserCredentials changedNameFrom(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword(), "SpiderMan");
    }
}
