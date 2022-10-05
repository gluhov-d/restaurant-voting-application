package com.github.restaurantvoting.repository;

import com.github.restaurantvoting.error.DataConflictException;
import com.github.restaurantvoting.model.UserVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserVoteRepository extends BaseRepository<UserVote> {

    @Query("SELECT v FROM UserVote v WHERE v.id = :id AND v.user.id = :userId")
    Optional<UserVote> get(int id, int userId);

    @Query("SELECT v FROM  UserVote v WHERE v.user.id = :userId")
    List<UserVote> getAllByUser(int userId);

    @Query("SELECT v FROM UserVote  v WHERE v.localDate = :localDate AND v.user.id = :userId")
    Optional<UserVote> getByDate(int userId, LocalDate localDate);

    default UserVote checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Voting id=" + id + " doesn't belong to user id=" + userId));
    }
}