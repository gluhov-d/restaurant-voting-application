package com.github.restaurantvoting.repository;

import com.github.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.github.restaurantvoting.util.validation.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    // https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id = ?1")
    Optional<Restaurant> getWithDishes(int id);

    @Query("SELECT COUNT (r) FROM Restaurant r WHERE r.id = ?1")
    int get(int id);

    default void checkExistence(int id) {
        checkModification(get(id), id);
    }
}