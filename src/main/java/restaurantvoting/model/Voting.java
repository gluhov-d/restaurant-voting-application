package restaurantvoting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "voting", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "voted"}, name = "voting_unique_user_voted_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "voted", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date voted = new Date();

    public Voting(Integer id, User user, Restaurant restaurant) {
        this(id, user, restaurant, new Date());
    }

    public Voting(Integer id, User user, Restaurant restaurant, Date voted) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voted = voted;
    }
}
