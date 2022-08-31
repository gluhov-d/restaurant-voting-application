package restaurantvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "local_date"}, name = "dish_unique_name_localdate_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "price")
    @Size(min = 1)
    private Integer price;

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    public Dish(Integer id, LocalDate localDate, String name, Integer price) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }
}
