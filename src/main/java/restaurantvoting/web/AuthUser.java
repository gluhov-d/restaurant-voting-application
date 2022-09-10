package restaurantvoting.web;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import restaurantvoting.model.User;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public AuthUser(@NonNull restaurantvoting.model.User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}