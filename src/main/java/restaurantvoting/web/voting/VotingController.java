package restaurantvoting.web.voting;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurantvoting.model.Voting;
import restaurantvoting.repository.VotingRepository;
import restaurantvoting.service.VotingService;
import restaurantvoting.web.AuthUser;

import java.net.URI;

import static restaurantvoting.web.voting.VotingController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VotingController {
    final static String REST_URL = "/api/restaurants";

    private final VotingRepository repository;
    private final VotingService service;

    @GetMapping("/{restaurantId}/votes/{id}")
    public ResponseEntity<Voting> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("get restaurant {} vote {} for user {}", restaurantId, id, authUser.id());
        return ResponseEntity.of(repository.findById(id));
    }

    @PutMapping(value = "/{restaurantId}/votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update user {} vote {} for restaurant {}", userId, id, restaurantId);
        repository.checkBelong(id, userId);
        service.save(userId, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/votes")
    public ResponseEntity<Voting> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        int userId = authUser.id();
        log.info("user {} vote for restaurant {}", userId, restaurantId);
        Voting created = service.save(userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}