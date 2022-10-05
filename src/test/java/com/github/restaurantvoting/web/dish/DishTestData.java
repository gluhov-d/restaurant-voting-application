package com.github.restaurantvoting.web.dish;

import com.github.restaurantvoting.model.Dish;
import com.github.restaurantvoting.web.MatcherFactory;

import java.time.Month;
import java.util.List;

import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.mirazurRestaurant;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final MatcherFactory.Matcher<Dish> DISH_WITH_RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Dish.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("restaurant.dishes", "restaurant.votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int DISH_ID = 1;
    public static final int MIRAZUR_RESTAURANT_ID = 1;
    public static final int NOMA_DISH_ID = 6;
    public static final int DISH_NOT_FOUND_ID = 100;

    public static final Dish mirazurDish1 = new Dish(DISH_ID, "Goose", of(2022, Month.AUGUST, 25), 25000);
    public static final Dish mirazurDish2 = new Dish(DISH_ID + 1, "Beef", of(2022, Month.AUGUST, 25), 20000);
    public static final Dish mirazurDish3 = new Dish(DISH_ID + 2, "Frankfurters", of(2022, Month.AUGUST, 25), 15000);
    public static final Dish nomaDish1 = new Dish(DISH_ID + 3, "Tuna", of(2022, Month.AUGUST, 25), 35000);
    public static final Dish nomaDish2 = new Dish(DISH_ID + 4, "Lobster", of(2022, Month.AUGUST, 25), 40000);
    public static final Dish nomaDish3 = new Dish(DISH_ID + 5, "Carp", of(2022, Month.AUGUST, 25), 43000);
    public static final Dish mirazurDish4 = new Dish(DISH_ID + 6, "Tuna", of(2022, Month.AUGUST, 26), 35000);
    public static final Dish mirazurDish5 = new Dish(DISH_ID + 7, "Lobster", of(2022, Month.AUGUST, 26), 40000);
    public static final Dish mirazurDish6 = new Dish(DISH_ID + 8, "Carp", of(2022, Month.AUGUST, 26), 43000);

    public static final List<Dish> dishesForOneDay = List.of(mirazurDish2, mirazurDish3, mirazurDish1);
    public static final List<Dish> allDishesMirazurRestaurant = List.of(mirazurDish6, mirazurDish5, mirazurDish4, mirazurDish2, mirazurDish3, mirazurDish1);

    static {
        mirazurDish1.setRestaurant(mirazurRestaurant);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "Updated Goose", mirazurDish1.getLocalDate().plusDays(1), 30000);
    }
}