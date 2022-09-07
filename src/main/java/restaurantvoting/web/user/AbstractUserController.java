package restaurantvoting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import restaurantvoting.model.User;
import restaurantvoting.repository.UserRepository;

import static restaurantvoting.util.UserUtil.prepareToSave;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    public ResponseEntity<User> get(int id) {
        log.info("get user {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete user {}", id);
        repository.deleteExisted(id);
    }

    public ResponseEntity<User> getWithVotes(int id) {
        log.info("get user {} with votes", id);
        return ResponseEntity.of(repository.getWithVotes(id));
    }

    protected User prepareAndSave(User user) {
        return repository.save(prepareToSave(user));
    }
}
