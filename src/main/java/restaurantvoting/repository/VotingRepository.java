package restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import restaurantvoting.model.Voting;

@Transactional(readOnly = true)
public interface VotingRepository extends BaseRepository<Voting> {
}