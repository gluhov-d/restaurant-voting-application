package com.github.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "voting", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "voting_unique_user_datetime_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-votes") // https://stackoverflow.com/a/20271621/2161414
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference(value = "restaurant-votes") // https://stackoverflow.com/a/20271621/2161414
    private Restaurant restaurant;

    // https://stackoverflow.com/a/58771441/2161414
    @Column(name = "date_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @NotNull
    private LocalDateTime dateTime;

    public Voting(Integer id, User user, Restaurant restaurant) {
        this(id, user, restaurant, LocalDateTime.now());
    }

    public Voting(Integer id, User user, Restaurant restaurant, LocalDateTime dateTime) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
    }
}