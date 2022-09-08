package restaurantvoting.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurantvoting.model.Dish;
import restaurantvoting.repository.DishRepository;
import restaurantvoting.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurantvoting.util.DateTimeUtil.dayOrMax;
import static restaurantvoting.util.DateTimeUtil.dayOrMin;
import static restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/restaurants";

    private final DishRepository repository;

    private final DishService service;

    @GetMapping("/{restaurantId}/dishes/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        Dish dish = repository.checkBelong(id, restaurantId);
        repository.delete(dish);
    }

    @GetMapping(value = "/{restaurantId}/dishes")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        assureIdConsistent(dish, id);
        repository.checkBelong(id, restaurantId);
        service.save(dish, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        Dish created = service.save(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(dish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}/dishes/filter")
    public List<Dish> getBetween(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @PathVariable int restaurantId) {
        log.info("get between dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return repository.getBetweenHalfOpen(dayOrMin(startDate), dayOrMax(endDate), restaurantId);
    }
}