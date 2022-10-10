package com.github.restaurantvoting.web.dish;

import com.github.restaurantvoting.model.Dish;
import com.github.restaurantvoting.repository.DishRepository;
import com.github.restaurantvoting.to.DishTo;
import com.github.restaurantvoting.util.DishUtil;
import com.github.restaurantvoting.util.JsonUtil;
import com.github.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static com.github.restaurantvoting.web.dish.DishTestData.*;
import static com.github.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishController.REST_URL + "/" + MIRAZUR_RESTAURANT_ID + "/dishes/";
    private static final String ADMIN_REST_URL = DishController.ADMIN_REST_URL + "/" + MIRAZUR_RESTAURANT_ID + "/dishes/";

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(mirazurDish1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + DISH_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.get(DISH_ID, MIRAZUR_RESTAURANT_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + NOMA_DISH_ID))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updatedDishTo = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDishTo)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getById(DISH_ID), DishUtil.updateFromTo(new Dish(mirazurDish1), updatedDishTo));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        DishTo newDishTo = new DishTo(null, "New Steak", of(2022, Month.AUGUST, 25), 45000);
        Dish newDish = DishUtil.createNewFromTo(newDishTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)));

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", LocalDate.now().minusDays(1).toString())
                .param("endDate", LocalDate.now().minusDays(1).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(dishesForOneDay));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/filter"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DISH_MATCHER.contentJson(allDishesMirazurRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        DishTo invalid = new DishTo(null, "Invalid", null, 30000);
        perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        DishTo invalidTo = new DishTo(DISH_ID, null, null, 5000);
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        DishTo invalidTo = new DishTo(DISH_ID, "<script>alert(777)</script>>", mirazurDish2.getLocalDate(), 500);
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        DishTo invalidTo = new DishTo(DISH_ID, "Beef", mirazurDish2.getLocalDate(), 100);
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}