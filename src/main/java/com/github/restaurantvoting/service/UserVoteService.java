package com.github.restaurantvoting.service;

import com.github.restaurantvoting.model.UserVote;
import com.github.restaurantvoting.repository.RestaurantRepository;
import com.github.restaurantvoting.repository.UserRepository;
import com.github.restaurantvoting.repository.UserVoteRepository;
import com.github.restaurantvoting.to.UserVoteTo;
import com.github.restaurantvoting.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.github.restaurantvoting.util.validation.ValidationUtil.checkVotingTime;

@Service
@AllArgsConstructor
public class UserVoteService {
    private final UserVoteRepository userVoteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public UserVote save(int userId, UserVoteTo userVoteTo) {
        UserVote userVote = new UserVote(null, userRepository.getById(userId), restaurantRepository.checkVotingRestaurant(userVoteTo.getRestaurantId()));
        return userVoteRepository.save(userVote);
    }

    @Transactional
    public UserVote update(int userId, UserVoteTo userVoteTo) {
        LocalDateTime votingDateTime = LocalDateTime.now(DateTimeUtil.getClock());
        UserVote userVote = userVoteRepository.checkExistence(userId, votingDateTime.toLocalDate());
        checkVotingTime(votingDateTime.toLocalTime());
        userVote.setRestaurant(restaurantRepository.checkVotingRestaurant(userVoteTo.getRestaurantId()));
        userVote.setDateTime(votingDateTime);
        return userVoteRepository.save(userVote);
    }
}
