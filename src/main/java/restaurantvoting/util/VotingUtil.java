package restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;

import static restaurantvoting.util.Util.isBetweenHalfOpen;

@UtilityClass
public class VotingUtil {

    public static final LocalTime END_OF_VOTING = LocalTime.of(11, 0, 0, 0);

    public static boolean isVotingOpen() {
        return isBetweenHalfOpen(LocalTime.now(), null, END_OF_VOTING);
    }
}
