package restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import restaurantvoting.model.Role;
import restaurantvoting.to.UserTo;

@UtilityClass
public class UserUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static restaurantvoting.model.User createNewFromTo(UserTo userTo) {
        return new restaurantvoting.model.User(null, userTo.getFirstName(), userTo.getLastName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static restaurantvoting.model.User updateFromTo(restaurantvoting.model.User user, UserTo userTo) {
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static restaurantvoting.model.User prepareToSave(restaurantvoting.model.User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}