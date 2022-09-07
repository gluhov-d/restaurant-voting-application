package restaurantvoting.web.voting;

import restaurantvoting.model.Voting;
import restaurantvoting.web.MatcherFactory;

import java.util.Date;

import static restaurantvoting.web.restaurant.RestaurantTestData.*;
import static restaurantvoting.web.user.UserTestData.*;

public class VotingTestData {
    public static final MatcherFactory.Matcher<Voting> VOTING_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Voting.class, "voted", "restaurant", "user");

    public static final int VOTE_ID = 1;
    public static final int USER_ID = 1;
    public static final int ADMIN_VOTE_ID = 3;
    public static final int VOTE_NOT_FOUND = 100;

    public static final Voting userVoting = new Voting(VOTE_ID, user, mirazurRestaurant);
    public static final Voting anotherUserVoting = new Voting(VOTE_ID + 1, user, nomaRestaurant);
    public static final Voting adminVoting = new Voting(ADMIN_VOTE_ID, admin, nomaRestaurant);

    public static Voting getNew() { return new Voting(null, user, mirazurRestaurant); }

    public static Voting getUpdated() {
        return new Voting(VOTE_ID, admin, mirazurRestaurant);
    }
}
