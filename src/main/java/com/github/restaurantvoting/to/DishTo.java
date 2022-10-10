package com.github.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    @Min(value = 1)
    int price;

    @NotNull
    LocalDate localDate;

    public DishTo(Integer id, String name, LocalDate localDate, int price) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }
}