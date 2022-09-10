package restaurantvoting.web.voting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurantvoting.model.Voting;
import restaurantvoting.repository.VotingRepository;
import restaurantvoting.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurantvoting.util.validation.ValidationUtil.EXCEPTION_VOTING_CLOSED;
import static restaurantvoting.web.restaurant.RestaurantTestData.MIRAZUR_RESTAURANT_ID;
import static restaurantvoting.web.restaurant.RestaurantTestData.NOMA_RESTAURANT_ID;
import static restaurantvoting.web.user.UserTestData.USER_MAIL;
import static restaurantvoting.web.voting.VotingTestData.*;

class VotingControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VotingController.REST_URL + "/";

    @Autowired
    private VotingRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_MATCHER.contentJson(userVoting1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    @EnabledIf(value = "restaurantvoting.util.VotingUtil#isVotingOpen()", disabledReason = "Voting is closed")
    void createWithLocation() throws Exception {
        Voting newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + MIRAZUR_RESTAURANT_ID));

        Voting created = VOTING_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTING_MATCHER.assertMatch(created, newVote);
        VOTING_MATCHER.assertMatch(repository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    @DisabledIf(value = "restaurantvoting.util.VotingUtil#isVotingOpen()", disabledReason = "Voting is open")
    void createWhenVotingIsClosed() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + MIRAZUR_RESTAURANT_ID))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_VOTING_CLOSED)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    @EnabledIf(value = "restaurantvoting.util.VotingUtil#isVotingOpen()", disabledReason = "Voting is closed")
    void update() throws Exception {
        Voting updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID + "/restaurants/" + NOMA_RESTAURANT_ID))
                .andExpect(status().isNoContent());

        VOTING_MATCHER.assertMatch(repository.getById(VOTE_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    @DisabledIf(value = "restaurantvoting.util.VotingUtil#isVotingOpen()", disabledReason = "Voting is open")
    void updateWhenVotingIsClosed() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID + "/restaurants/" + NOMA_RESTAURANT_ID))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_VOTING_CLOSED)));
    }
}