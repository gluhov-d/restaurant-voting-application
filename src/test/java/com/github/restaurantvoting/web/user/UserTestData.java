package com.github.restaurantvoting.web.user;

import com.github.restaurantvoting.model.Role;
import com.github.restaurantvoting.model.User;
import com.github.restaurantvoting.util.JsonUtil;
import com.github.restaurantvoting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.github.restaurantvoting.web.voting.VotingTestData.adminVoting1;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "votes", "password");
    public static final MatcherFactory.Matcher<User> USER_WITH_VOTES_MATCHER =
            MatcherFactory.usingAssertions(User.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "votes.restaurant", "votes.dateTime", "votes.localDate", "votes.user", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int USER_NOT_FOUND_ID = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "Dmitrii", "Petrov", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Anton", "Bashirov", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

    static {
        admin.setVotes(Collections.singleton(adminVoting1));
    }

    public static User getNew() {
        return new User(null, "NewFirstName", "NewLastName", "new@mail.ru", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedFirstName", "UpdatedLastName", USER_MAIL, "updatedPass", false, new Date(), List.of(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}