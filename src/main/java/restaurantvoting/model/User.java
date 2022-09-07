package restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;
import restaurantvoting.HasId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    private String email;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 128)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 128)
    @NotBlank
    private String lastName;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("voted DESC")
    @JsonManagedReference(value = "user-votes") // https://stackoverflow.com/a/20271621/2161414
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    private Set<Voting> votes;

    public User(User u) {
        this(u.id, u.firstName, u.lastName, u.email, u.password, u.enabled, u.registered, u.roles);
    }

    public User(Integer id, String firstName, String lastName, String email, String password, Role role, Role... roles) {
        this(id, firstName, lastName, email, password, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String firstName, String lastName, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User:" + id + "[" + email + "]";
    }
}