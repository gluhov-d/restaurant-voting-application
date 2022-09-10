package com.github.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.restaurantvoting.model.Voting;
import com.github.restaurantvoting.repository.RestaurantRepository;
import com.github.restaurantvoting.repository.UserRepository;
import com.github.restaurantvoting.repository.VotingRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Voting save(int userId, int restaurantId, LocalDateTime votingDateTime) {
        Voting voting = new Voting(null, userRepository.getById(userId), restaurantRepository.getById(restaurantId), votingDateTime);
        return votingRepository.save(voting);
    }

    @Transactional
    public Voting update(int userId, int restaurantId, int voteId, LocalDateTime votingDateTime) {
        Voting voting = new Voting(voteId, userRepository.getById(userId), restaurantRepository.getById(restaurantId), votingDateTime);
        return votingRepository.save(voting);
    }
}
