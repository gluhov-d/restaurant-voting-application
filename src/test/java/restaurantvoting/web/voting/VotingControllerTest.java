package restaurantvoting.web.voting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurantvoting.model.Voting;
import restaurantvoting.repository.VotingRepository;
import restaurantvoting.util.JsonUtil;
import restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurantvoting.web.voting.VotingTestData.*;

class VotingControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VotingController.REST_URL + "/";

    @Autowired
    private VotingRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + USER_ID + "/votes/" + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_MATCHER.contentJson(userVoting));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + USER_ID + "/votes/" + VOTE_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.get(VOTE_ID, USER_ID).isPresent());
    }

    @Test
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + USER_ID + "/votes/" + VOTE_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.get(VOTE_ID, USER_ID).isPresent());
    }

    @Test
    void createWithLocation() throws Exception {
        Voting newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/" + USER_ID + "/votes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Voting created = VOTING_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTING_MATCHER.assertMatch(created, newVote);
        VOTING_MATCHER.assertMatch(repository.getById(newId), newVote);
    }

    @Test
    void update() throws Exception {
        Voting updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + USER_ID + "/votes/" + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        VOTING_MATCHER.assertMatch(repository.getById(VOTE_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + USER_ID + "/votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_MATCHER.contentJson(userVoting, anotherUserVoting));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}