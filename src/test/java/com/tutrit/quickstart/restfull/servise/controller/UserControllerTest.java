package com.tutrit.quickstart.restfull.servise.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import com.tutrit.quickstart.restfull.servise.bean.GhbUser;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends BaseControllerTest {

    private final String ENDPOINT = "ghbUsers";
    private final String ID1 = "f8cf1713-e411-4558-af76-0b0d6129fef1";
    private final String ID2 = "f8cf1713-e411-4558-af76-0b0d6129fef2";
    private final TypeReference<PagedModel<EntityModel<GhbUser>>> pagedModelTypeReference = new TypeReference<>() {
    };
    private final TypeReference<EntityModel<GhbUser>> entityModelTypeReference = new TypeReference<>() {
    };

    @Test
    @Sql("/user.sql")
    @Transactional
    void getAll() throws Exception {
        final List<GhbUser> expected = List.of(makeUser1(),  makeUser2());
        final List<GhbUser> result = testGetPagedModel(makeUri(ENDPOINT), pagedModelTypeReference);
        assertEquals(expected, result);
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void getById() throws Exception {
        GhbUser user = testGetEntityModel(makeUri(ENDPOINT, ID1), entityModelTypeReference);
        assertEquals(user, makeUser1());
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void getByTelegramChatId() throws Exception {
        GhbUser user = testGetEntityModel(makeUri(makeQuery("id", "telegram_chat_id1"), ENDPOINT, "search", "findByTelegramChatId"), entityModelTypeReference);
        assertEquals(user, makeUser1());
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void save() throws Exception {
        String id = testPost(makeUri(ENDPOINT), new GhbUser(null, "99999", "new user", "en", "email", "password"));
        GhbUser user = testGetEntityModel(makeUri(ENDPOINT, id), entityModelTypeReference);
        assertEquals(user, new GhbUser(UUID.fromString(id), "99999", "new user", "en", "email", "password"));
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void update() throws Exception {
        var uri = makeUri(ENDPOINT, ID1);
        GhbUser updated = makeUser1();
        updated.setEmail("updated");
        testPut(uri, updated);
        GhbUser user = testGetEntityModel(uri, entityModelTypeReference);
        assertEquals(user, updated);
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void change() throws Exception {
        var uri = makeUri(ENDPOINT, ID1);
        GhbUser changed = makeUser1();
        changed.setUserName("changed");
        testPatch(uri, new GhbUser(null, null, "changed", null, null, null));
        GhbUser user = testGetEntityModel(uri, entityModelTypeReference);
        assertEquals(user, changed);
    }

    @Test
    @Sql("/user.sql")
    @Transactional
    void deleteById() throws Exception {
        testDelete(makeUri(ENDPOINT, ID1));
    }

    @NotNull
    private GhbUser makeUser2() {
        return new GhbUser(UUID.fromString(ID2), "telegram_chat_id2", "userName2", "en", "email2", "password2");
    }

    @NotNull
    private GhbUser makeUser1() {
        return new GhbUser(UUID.fromString(ID1), "telegram_chat_id1", "userName1", "en", "email1", "password1");
    }
}
