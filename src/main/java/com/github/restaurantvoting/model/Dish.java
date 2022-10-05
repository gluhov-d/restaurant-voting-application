package com.github.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "local_date", "name"}, name = "dish_unique_restaurant_localdate_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Dish.class)
public class Dish extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "price", nullable = false)
    @Min(value = 1)
    private int price;

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    public Dish(Integer id, String name, LocalDate localDate, int price) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "Dish:" + id + "[" + localDate + "]";
    }
}