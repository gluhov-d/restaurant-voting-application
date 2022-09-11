package com.github.restaurantvoting.web;

import com.github.restaurantvoting.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}