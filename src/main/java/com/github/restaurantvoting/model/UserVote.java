package com.github.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.restaurantvoting.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "local_date"}, name = "user_vote_unique_user_localdate_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = UserVote.class)
public class UserVote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-votes") // https://stackoverflow.com/a/20271621/2161414
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // https://stackoverflow.com/a/58771441/2161414
    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @JsonIgnore
    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    public UserVote(Integer id, User user, Restaurant restaurant) {
        this(id, user, restaurant, LocalDateTime.now(DateTimeUtil.getClock()));
    }

    public UserVote(Integer id, User user, Restaurant restaurant, LocalDateTime dateTime) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.localDate = dateTime.toLocalDate();
    }

    @Override
    public String toString() {
        return "Vote:" + id + "[" + dateTime + "]";
    }
}