package com.github.restaurantvoting.repository;

import com.github.restaurantvoting.error.DataConflictException;
import com.github.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    // https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.dishes d WHERE r.id = ?1 ORDER BY r.name, d.localDate DESC, d.name ASC")
    Optional<Restaurant> getWithDishes(int id);

    @Query("SELECT r FROM Restaurant r WHERE r.id = ?1")
    Optional<Restaurant> get(int id);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes ORDER BY r.name")
    List<Restaurant> getAllWithDishes();

    default Restaurant checkVotingRestaurant(int id) {
        return get(id).orElseThrow(
                () -> new DataConflictException("Restaurant with id=" + id + " not exist"));
    }
}