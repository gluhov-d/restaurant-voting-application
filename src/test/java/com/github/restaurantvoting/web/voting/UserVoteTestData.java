package com.github.restaurantvoting.web.voting;

import com.github.restaurantvoting.model.UserVote;
import com.github.restaurantvoting.web.MatcherFactory;

import java.util.List;

import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.mirazurRestaurant;
import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.nomaRestaurant;
import static com.github.restaurantvoting.web.user.UserTestData.admin;
import static com.github.restaurantvoting.web.user.UserTestData.user;

public class UserVoteTestData {
    public static final MatcherFactory.Matcher<UserVote> VOTING_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserVote.class, "dateTime", "localDate", "user", "restaurant");

    public static final int VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int VOTE_NOT_FOUND = 100;

    public static final UserVote userVoting1 = new UserVote(VOTE_ID, user, mirazurRestaurant);
    public static final UserVote userVoting2 = new UserVote(VOTE_ID + 2, user, nomaRestaurant);
    public static final UserVote adminVoting1 = new UserVote(ADMIN_VOTE_ID, admin, nomaRestaurant);

    public static final List<UserVote> userVotes = List.of(userVoting1, userVoting2);
}