package com.github.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import com.github.restaurantvoting.HasIdAndEmail;
import com.github.restaurantvoting.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserTo extends BaseTo implements HasIdAndEmail {

    @NotBlank
    @Size(min = 2, max = 128)
    @NoHtml
    String firstName;

    @NotBlank
    @Size(min = 2, max = 128)
    @NoHtml
    String lastName;

    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    public UserTo(Integer id, String firstName, String lastName, String email, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + "[" + email + "]";
    }
}
