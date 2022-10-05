package com.github.restaurantvoting.util;

import com.github.restaurantvoting.model.Restaurant;
import com.github.restaurantvoting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantTo> getFilteredTosByDate(Collection<Restaurant> restaurants, LocalDate localDate) {
        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(), DishUtil.getFilteredTos(restaurant.getDishes(), localDate)))
                .collect(Collectors.toList());
    }
}