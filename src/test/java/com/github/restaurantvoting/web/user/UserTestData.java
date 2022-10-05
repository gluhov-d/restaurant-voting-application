package com.github.restaurantvoting.web.user;

import com.github.restaurantvoting.model.Role;
import com.github.restaurantvoting.model.User;
import com.github.restaurantvoting.util.DateTimeUtil;
import com.github.restaurantvoting.util.JsonUtil;
import com.github.restaurantvoting.web.MatcherFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.github.restaurantvoting.web.voting.UserVoteTestData.adminVoting1;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "votes", "password");

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
        return new User(null, "NewFirstName", "NewLastName", "new@mail.ru", "newPass",
                false, LocalDateTime.now(DateTimeUtil.getClock()), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedFirstName", "UpdatedLastName", USER_MAIL, "updatedPass",
                false, LocalDateTime.now(DateTimeUtil.getClock()), List.of(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}