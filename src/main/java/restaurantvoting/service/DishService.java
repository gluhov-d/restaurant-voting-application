package restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurantvoting.model.Dish;
import restaurantvoting.repository.DishRepository;
import restaurantvoting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        return dishRepository.save(dish);
    }
}