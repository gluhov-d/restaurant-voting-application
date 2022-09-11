package com.github.restaurantvoting.web.voting;

import com.github.restaurantvoting.model.Voting;
import com.github.restaurantvoting.web.MatcherFactory;

import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.mirazurRestaurant;
import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.nomaRestaurant;
import static com.github.restaurantvoting.web.user.UserTestData.admin;
import static com.github.restaurantvoting.web.user.UserTestData.user;

public class VotingTestData {
    public static final MatcherFactory.Matcher<Voting> VOTING_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Voting.class, "dateTime", "localDate", "user", "restaurant");

    public static final int VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int VOTE_NOT_FOUND = 100;

    public static final Voting userVoting1 = new Voting(VOTE_ID, user, mirazurRestaurant);
    public static final Voting adminVoting1 = new Voting(ADMIN_VOTE_ID, admin, nomaRestaurant);

    public static Voting getNew() {
        return new Voting(null, null, mirazurRestaurant);
    }

    public static Voting getUpdated() {
        return new Voting(VOTE_ID, user, nomaRestaurant);
    }
}