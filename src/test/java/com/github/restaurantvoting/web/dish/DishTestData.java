package com.github.restaurantvoting.web.dish;

import com.github.restaurantvoting.model.Dish;
import com.github.restaurantvoting.to.DishTo;
import com.github.restaurantvoting.util.DateTimeUtil;
import com.github.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.mirazurRestaurant;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID = 1;
    public static final int MIRAZUR_RESTAURANT_ID = 1;
    public static final int NOMA_DISH_ID = 6;
    public static final int DISH_NOT_FOUND_ID = 100;

    public static final Dish mirazurDish1 = new Dish(DISH_ID, "Goose", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 25000);
    public static final Dish mirazurDish2 = new Dish(DISH_ID + 1, "Beef", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 20000);
    public static final Dish mirazurDish3 = new Dish(DISH_ID + 2, "Frankfurters", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 15000);
    public static final Dish nomaDish1 = new Dish(DISH_ID + 3, "Tuna", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 35000);
    public static final Dish nomaDish2 = new Dish(DISH_ID + 4, "Lobster", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 40000);
    public static final Dish nomaDish3 = new Dish(DISH_ID + 5, "Carp", LocalDate.now(DateTimeUtil.getClock()).minusDays(1), 43000);
    public static final Dish mirazurDish4 = new Dish(DISH_ID + 6, "Tuna", LocalDate.now(DateTimeUtil.getClock()), 35000);
    public static final Dish mirazurDish5 = new Dish(DISH_ID + 7, "Lobster", LocalDate.now(DateTimeUtil.getClock()), 40000);
    public static final Dish mirazurDish6 = new Dish(DISH_ID + 8, "Carp", LocalDate.now(DateTimeUtil.getClock()), 43000);

    public static final List<Dish> dishesForOneDay = List.of(mirazurDish2, mirazurDish3, mirazurDish1);
    public static final List<Dish> allDishesMirazurRestaurant = List.of(mirazurDish6, mirazurDish5, mirazurDish4, mirazurDish2, mirazurDish3, mirazurDish1);

    static {
        mirazurDish1.setRestaurant(mirazurRestaurant);
    }

    public static DishTo getUpdatedTo() {
        return new DishTo(DISH_ID, "Updated Goose", mirazurDish1.getLocalDate().plusDays(1), 30000);
    }
}