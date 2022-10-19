package order;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Data
public class Order {

    private List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order getIngredientsList() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa79","61c0c5a71d1f82001bdaaa6c"));
    }

    public static Order withoutIngredients() {
        return new Order(List.of());
    }

    public static Order getIncorrectIngredients() {
        return new Order(List.of(
                RandomStringUtils.randomAlphanumeric(24),
                RandomStringUtils.randomAlphanumeric(24)));
    }
}
