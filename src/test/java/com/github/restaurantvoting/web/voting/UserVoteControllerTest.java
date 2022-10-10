package com.github.restaurantvoting.web.voting;

import com.github.restaurantvoting.model.UserVote;
import com.github.restaurantvoting.repository.UserVoteRepository;
import com.github.restaurantvoting.to.UserVoteTo;
import com.github.restaurantvoting.util.DateTimeUtil;
import com.github.restaurantvoting.util.JsonUtil;
import com.github.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.github.restaurantvoting.util.validation.ValidationUtil.EXCEPTION_VOTING_CLOSED;
import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.MIRAZUR_RESTAURANT_ID;
import static com.github.restaurantvoting.web.restaurant.RestaurantTestData.NOMA_RESTAURANT_ID;
import static com.github.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static com.github.restaurantvoting.web.voting.UserVoteTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserVoteController.REST_URL + "/";

    @Autowired
    private UserVoteRepository repository;

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
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-date")
                .param("votingDate", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_MATCHER.contentJson(userVoting1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_MATCHER.contentJson(userVotes));
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
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        UserVoteTo newTo = new UserVoteTo(null, MIRAZUR_RESTAURANT_ID);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserVote created = VOTING_MATCHER.readFromJson(action);
        Assertions.assertEquals(repository.getById(created.id()).getRestaurant().id(), newTo.getRestaurantId());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicate() throws Exception {
        UserVoteTo newTo = new UserVoteTo(null, MIRAZUR_RESTAURANT_ID);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserVoteTo updatedTo = new UserVoteTo(null, NOMA_RESTAURANT_ID);
        Clock clock = Clock.fixed(Instant.parse(LocalDate.now(DateTimeUtil.getClock()) + "T10:59:30.00Z"), ZoneId.of("UTC"));
        DateTimeUtil.setClock(clock);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertEquals(repository.getById(userVoting2.id()).getRestaurant().id(), updatedTo.getRestaurantId());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWhenIsClosed() throws Exception {
        UserVoteTo updatedTo = new UserVoteTo(null, NOMA_RESTAURANT_ID);
        Clock clock = Clock.fixed(Instant.parse(LocalDate.now(DateTimeUtil.getClock()) + "T12:15:30.00Z"), ZoneId.of("UTC"));
        DateTimeUtil.setClock(clock);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_VOTING_CLOSED)));
    }
}