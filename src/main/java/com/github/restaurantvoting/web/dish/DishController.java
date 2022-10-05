package com.github.restaurantvoting.web.dish;

import com.github.restaurantvoting.model.Dish;
import com.github.restaurantvoting.repository.DishRepository;
import com.github.restaurantvoting.service.DishService;
import com.github.restaurantvoting.to.DishTo;
import com.github.restaurantvoting.util.DishUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.restaurantvoting.util.DateTimeUtil.dayOrMax;
import static com.github.restaurantvoting.util.DateTimeUtil.dayOrMin;
import static com.github.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/restaurants";
    static final String ADMIN_REST_URL = "/api/admin/restaurants";

    private final DishRepository repository;
    private final DishService service;

    @GetMapping(REST_URL + "/{restaurantId}/dishes/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @DeleteMapping(ADMIN_REST_URL + "/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        Dish dish = repository.checkBelong(id, restaurantId);
        repository.delete(dish);
    }

    @GetMapping(REST_URL + "/{restaurantId}/dishes/{id}/with-restaurant")
    public ResponseEntity<Dish> getWithRestaurant(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} with restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.getWithRestaurant(id, restaurantId));
    }

    @PutMapping(value = ADMIN_REST_URL + "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        assureIdConsistent(dish, id);
        service.save(dish, restaurantId);
    }

    @PostMapping(value = ADMIN_REST_URL + "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dishTo, restaurantId);
        checkNew(dishTo);
        Dish created = service.save(DishUtil.createNewFromTo(dishTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(dishTo.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(REST_URL + "/{restaurantId}/dishes/filter")
    public List<Dish> getBetween(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @PathVariable int restaurantId) {
        log.info("get dishes between dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return repository.getBetween(dayOrMin(startDate), dayOrMax(endDate), restaurantId);
    }
}