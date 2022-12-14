package com.github.restaurantvoting.repository;

import com.github.restaurantvoting.error.DataConflictException;
import com.github.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurantId")
    Optional<Dish> get(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.localDate >= :startDate AND d.localDate <= :endDate ORDER BY d.localDate DESC, d.name ASC")
    List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId);

    default Dish checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + " doesn't belong to restaurant id=" + restaurantId));
    }
}