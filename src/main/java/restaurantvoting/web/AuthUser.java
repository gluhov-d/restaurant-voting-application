package restaurantvoting.web;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import restaurantvoting.model.User;
import restaurantvoting.to.UserTo;
import restaurantvoting.util.UserUtil;

@Getter
@ToString(of = "userTo")
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private UserTo userTo;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        setTo(UserUtil.asTo(user, null));
    }

    public void setTo(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    public int id() {
        return userTo.id();
    }
}