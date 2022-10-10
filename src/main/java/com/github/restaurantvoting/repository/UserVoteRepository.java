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

    @Query("SELECT v FROM UserVote v JOIN FETCH v.restaurant WHERE v.id = :id AND v.user.id = :userId")
    Optional<UserVote> get(int id, int userId);

    @Query("SELECT v FROM UserVote v JOIN FETCH v.restaurant WHERE v.user.id = :userId")
    List<UserVote> getAllByUser(int userId);

    @Query("SELECT v FROM UserVote v JOIN FETCH v.restaurant WHERE v.localDate = :localDate AND v.user.id = :userId")
    Optional<UserVote> getByDate(int userId, LocalDate localDate);

    default UserVote checkExistence(int userId, LocalDate localDate) {
        return getByDate(userId, localDate).orElseThrow(
                () -> new DataConflictException("Can't update voting that doesn't exist"));
    }
}