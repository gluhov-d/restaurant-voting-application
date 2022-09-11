package com.github.restaurantvoting.web.voting;

import com.github.restaurantvoting.model.Voting;
import com.github.restaurantvoting.repository.VotingRepository;
import com.github.restaurantvoting.service.VotingService;
import com.github.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.github.restaurantvoting.util.validation.ValidationUtil.checkVotingTime;
import static com.github.restaurantvoting.web.voting.VotingController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VotingController {
    final static String REST_URL = "/api/votes";

    private final VotingRepository repository;
    private final VotingService service;

    @GetMapping("/{id}")
    public ResponseEntity<Voting> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id, authUser.id()));
    }

    @PutMapping(value = "/{id}/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @PathVariable int restaurantId) {
        int userId = authUser.id();
        log.info("update vote for user {}", userId);
        LocalDateTime votingDateTime = LocalDateTime.now();
        checkVotingTime(votingDateTime.toLocalTime());
        repository.checkBelong(id, userId);
        service.update(userId, restaurantId, id, votingDateTime);
    }

    @PostMapping(value = "/restaurants/{restaurantId}")
    public ResponseEntity<Voting> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        int userId = authUser.id();
        log.info("user {} vote for restaurant {}", userId, restaurantId);
        LocalDateTime votingDateTime = LocalDateTime.now();
        checkVotingTime(votingDateTime.toLocalTime());
        Voting voting;
        Optional<Voting> checkNew = repository.checkNewTodayVoting(userId, votingDateTime.toLocalDate());
        if (checkNew.isPresent()) {
            voting = service.update(userId, restaurantId, checkNew.get().id(), votingDateTime);
        } else {
            voting = service.save(userId, restaurantId, votingDateTime);
        }

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(voting.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(voting);
    }
}