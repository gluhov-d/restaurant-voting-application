package restaurantvoting.web.voting;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurantvoting.model.Voting;
import restaurantvoting.repository.VotingRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static restaurantvoting.util.validation.ValidationUtil.checkNew;
import static restaurantvoting.web.voting.VotingController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VotingController {
    final static String REST_URL = "/api/profile";

    private final VotingRepository repository;

    @GetMapping("/{userId}/votes/{id}")
    public ResponseEntity<Voting> get(@PathVariable int userId, @PathVariable int id) {
        log.info("get vote {} for user {}", id, userId);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping(value = "/{userId}/votes")
    public List<Voting> getAll(@PathVariable int userId) {
        log.info("get all votes for user {}", userId);
        return repository.getAll(userId);
    }

    @DeleteMapping("/{userId}/votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int userId, @PathVariable int id) {
        log.info("delete vote {} for user {}", id, userId);
        Voting voting = repository.checkBelong(id, userId);
        repository.delete(voting);
    }

    @PutMapping(value = "/{userId}/votes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Voting voting, @PathVariable int userId, @PathVariable int id) {
        log.info("update vote {} for user {}", id, userId);
        assureIdConsistent(voting, id);
        repository.checkBelong(id, userId);
        repository.save(voting);
    }

    @PostMapping(value = "/{userId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Voting> createWithLocation(@Valid @RequestBody Voting voting, @PathVariable int userId) {
        log.info("create vote {} for user {}", voting, userId);
        checkNew(voting);
        Voting created = repository.save(voting);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(voting.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}