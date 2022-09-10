package restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class VotingUtil {

    public static final LocalTime END_OF_VOTING = LocalTime.of(11, 0, 0, 0);

}
