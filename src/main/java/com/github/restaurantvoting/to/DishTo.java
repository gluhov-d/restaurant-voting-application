package com.github.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    int price;

    @NotNull
    LocalDate localDate;

    public DishTo(Integer id, String name, LocalDate localDate, int price) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }
}