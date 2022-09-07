package restaurantvoting.web.restaurant;

import restaurantvoting.model.Restaurant;
import restaurantvoting.web.MatcherFactory;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static restaurantvoting.web.dish.DishTestData.*;
import static restaurantvoting.web.voting.VotingTestData.adminVoting;
import static restaurantvoting.web.voting.VotingTestData.userVoting;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes", "votes");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dishes.restaurant", "votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int MIRAZUR_RESTAURANT_ID = 1;
    public static final int NOMA_RESTAURANT_ID = 2;
    public static final int NOT_FOUND_ID = 100;

    public static final Restaurant mirazurRestaurant = new Restaurant(MIRAZUR_RESTAURANT_ID, "Mirazur");
    public static final Restaurant nomaRestaurant = new Restaurant(NOMA_RESTAURANT_ID, "Noma");

    public static final List<Restaurant> restaurants = List.of(mirazurRestaurant, nomaRestaurant);

    static {
        mirazurRestaurant.setDishes(dishes);
        mirazurRestaurant.setVotes(Collections.singleton(userVoting));
        nomaRestaurant.setDishes(List.of(nomaDish1, nomaDish2, nomaDish3));
        nomaRestaurant.setVotes(Collections.singleton(adminVoting));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Созданный ресторан");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MIRAZUR_RESTAURANT_ID, "Обновленный Mirazur");
    }
}
