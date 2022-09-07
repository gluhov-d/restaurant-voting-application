package restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import restaurantvoting.error.DataConflictException;
import restaurantvoting.model.Voting;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VotingRepository extends BaseRepository<Voting> {

    @Query("SELECT v FROM Voting v WHERE v.id = :id AND v.user.id = :userId")
    Optional<Voting> get(int id, int userId);

    @Query("SELECT v FROM Voting v JOIN FETCH v.user WHERE v.id = :id AND v.user.id = :userId")
    Optional<Voting> getWithUser(int id, int userId);

    @Query("SELECT v FROM Voting  v WHERE v.user.id = :userId ORDER BY v.voted DESC")
    List<Voting> getAll(int userId);

    default Voting checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Voting id=" + id + " doesn't belong to user id=" + userId));
    }
}