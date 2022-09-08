package restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import restaurantvoting.model.Role;
import restaurantvoting.model.User;
import restaurantvoting.model.Voting;
import restaurantvoting.to.UserTo;

import java.time.LocalTime;

import static restaurantvoting.util.Util.isBetweenHalfOpen;

@UtilityClass
public class UserUtil {

    private static final LocalTime END_OF_VOTING = LocalTime.of(11, 0, 0, 0);
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getFirstName(), userTo.getLastName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserTo asTo(User user, Voting voting) {
        return new UserTo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), voting != null && checkVoting(voting));
    }

    private static boolean checkVoting(Voting voting) {
        return isBetweenHalfOpen(voting.getDateTime().toLocalTime(), null, END_OF_VOTING);
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}