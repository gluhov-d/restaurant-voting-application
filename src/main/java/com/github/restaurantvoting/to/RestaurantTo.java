package com.github.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    List<DishTo> dishTos;

    public RestaurantTo(Integer id, String name, List<DishTo> dishTos) {
        super(id, name);
        this.dishTos = dishTos;
    }
}