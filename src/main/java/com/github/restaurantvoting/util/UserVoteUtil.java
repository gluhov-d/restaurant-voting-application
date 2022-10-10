package com.github.restaurantvoting.util;

import com.github.restaurantvoting.model.UserVote;
import com.github.restaurantvoting.to.UserVoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserVoteUtil {

    public static UserVoteTo createTo(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getRestaurant().id());
    }
}