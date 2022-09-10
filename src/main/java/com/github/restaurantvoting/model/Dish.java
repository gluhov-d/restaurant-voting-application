package com.github.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "local_date"}, name = "dish_unique_name_localdate_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    // https://stackoverflow.com/a/20271621/2161414
    @JsonBackReference(value = "restaurant-dishes")
    private Restaurant restaurant;

    @Column(name = "price", nullable = false)
    @Min(value = 1)
    @NotNull
    private int price;

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    public Dish(Integer id, String name, LocalDate localDate, int price) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }
}