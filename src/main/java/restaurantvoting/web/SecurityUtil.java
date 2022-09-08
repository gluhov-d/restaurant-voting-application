package restaurantvoting.web;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import restaurantvoting.model.User;
import restaurantvoting.to.UserTo;

import static java.util.Objects.requireNonNull;

@UtilityClass
public class SecurityUtil {

    public static AuthUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthUser) ? (AuthUser) principal : null;
    }

    public static AuthUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static UserTo authUser() {
        return get().getUserTo();
    }

    public static int authId() {
        return get().getUserTo().id();
    }
}