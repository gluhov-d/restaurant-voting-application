package restaurantvoting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurantvoting.model.User;
import restaurantvoting.to.UserTo;
import restaurantvoting.util.UserUtil;
import restaurantvoting.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;

import static restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static restaurantvoting.util.validation.ValidationUtil.checkNew;
import static restaurantvoting.web.user.ProfileController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register user {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody UserTo userTo) {
        assureIdConsistent(userTo, authUser.id());
        User user = repository.getById(authUser.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    @GetMapping("/with-votes")
    public ResponseEntity<restaurantvoting.model.User> getWithVotes(@AuthenticationPrincipal AuthUser authUser) {
        return super.getWithVotes(authUser.id());
    }
}