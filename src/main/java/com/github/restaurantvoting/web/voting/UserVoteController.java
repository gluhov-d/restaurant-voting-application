package com.github.restaurantvoting.web.voting;

import com.github.restaurantvoting.model.UserVote;
import com.github.restaurantvoting.repository.UserVoteRepository;
import com.github.restaurantvoting.service.UserVoteService;
import com.github.restaurantvoting.to.UserVoteTo;
import com.github.restaurantvoting.util.UserVoteUtil;
import com.github.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.restaurantvoting.util.validation.ValidationUtil.checkNew;
import static com.github.restaurantvoting.web.voting.UserVoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserVoteController {
    static final String REST_URL = "/api/votes";

    private final UserVoteRepository repository;
    private final UserVoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserVote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id, authUser.id()));
    }

    @GetMapping("/by-date")
    public ResponseEntity<UserVote> getByDate(@AuthenticationPrincipal AuthUser authUser,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate votingDate) {
        int userId = authUser.id();
        log.info("get vote for date {}", votingDate);
        return ResponseEntity.of(repository.getByDate(userId, votingDate));
    }

    @GetMapping
    public List<UserVote> getAllByUser(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get all votes for user {}", userId);
        return repository.getAllByUser(userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody UserVoteTo userVoteTo) {
        int userId = authUser.id();
        log.info("update vote for user {}", userId);
        service.update(userId, userVoteTo);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVoteTo> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody UserVoteTo userVoteTo) {
        int userId = authUser.id();
        log.info("user {} vote for restaurant {}", userId, userVoteTo.getRestaurantId());
        checkNew(userVoteTo);
        UserVote userVote = service.save(userId, userVoteTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(userVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(UserVoteUtil.createTo(userVote));
    }
}