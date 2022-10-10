package com.github.restaurantvoting.util;

import com.github.restaurantvoting.model.Dish;
import com.github.restaurantvoting.model.NamedEntity;
import com.github.restaurantvoting.to.DishTo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getLocalDate(), dishTo.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setLocalDate(dishTo.getLocalDate());
        return dish;
    }

    public static DishTo createTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getLocalDate(), dish.getPrice());
    }

    public static List<DishTo> getFilteredTos(Collection<Dish> dishes, LocalDate localDate) {
        return dishes.stream()
                .filter(dish -> dish.getLocalDate().equals(localDate))
                .sorted(Comparator.comparing(NamedEntity::getName))
                .map(DishUtil::createTo)
                .collect(Collectors.toList());
    }
}