package com.github.restaurantvoting.repository;

import com.github.restaurantvoting.error.DataConflictException;
import com.github.restaurantvoting.model.Voting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VotingRepository extends BaseRepository<Voting> {

    @Query("SELECT v FROM Voting v WHERE v.id = :id AND v.user.id = :userId")
    Optional<Voting> get(int id, int userId);

    @Query("SELECT v FROM Voting  v WHERE v.localDate = :localDate AND v.user.id = :userId")
    Optional<Voting> get(int userId, LocalDate localDate);

    default Voting checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Voting id=" + id + " doesn't belong to user id=" + userId));
    }

    default Optional<Voting> checkNewTodayVoting(int userId, LocalDate localDate) {
        return get(userId, localDate);
    }
}