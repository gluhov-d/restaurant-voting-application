package restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurantvoting.model.Voting;
import restaurantvoting.repository.RestaurantRepository;
import restaurantvoting.repository.UserRepository;
import restaurantvoting.repository.VotingRepository;

@Service
@AllArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Voting save(int restaurantId, int id) {
        Voting voting = new Voting(null, null, null);
        voting.setRestaurant(restaurantRepository.getById(restaurantId));
        voting.setUser(userRepository.getById(id));
        return votingRepository.save(voting);
    }
}
