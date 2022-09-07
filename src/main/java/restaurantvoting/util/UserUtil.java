package restaurantvoting.util;

import lombok.experimental.UtilityClass;
import restaurantvoting.model.Role;
import restaurantvoting.model.User;

import java.util.Collections;

@UtilityClass
public class UserUtil {

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        user.setRoles(Collections.singletonList(Role.USER));
        return user;
    }
}