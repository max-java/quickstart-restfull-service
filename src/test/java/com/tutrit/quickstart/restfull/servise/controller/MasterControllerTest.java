//package com.tutrit.quickstart.restapi.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.Test;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//import com.tutrit.quickstart.restapi.bean.GhbUser;
//import com.tutrit.quickstart.restapi.bean.Master;
//import com.tutrit.quickstart.restapi.util.AssociatedObject;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.MASTER_MODEL;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.MASTER_PAGE;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.USER_MODEL;
//
//public class MasterControllerTest extends BaseControllerTest {
//
//    private final String ENDPOINT = "masters";
//    private final String USER_ID1 = "f8cf1713-e411-4558-af76-0b0d6129fef1";
//    private final String USER_ID2 = "f8cf1713-e411-4558-af76-0b0d6129fef2";
//    private final String MASTER_ID1 = "14ded5c9-2b65-41c8-82ec-ad6ac17e2f51";
//    private final String MASTER_ID2 = "24ded5c9-2b65-41c8-82ec-ad6ac17e2f52";
//    private final UUID USER_UUID1 = UUID.fromString(USER_ID1);
//    private final UUID USER_UUID2 = UUID.fromString(USER_ID2);
//    private final UUID MASTER_UUID1 = UUID.fromString(MASTER_ID1);
//    private final UUID MASTER_UUID2 = UUID.fromString(MASTER_ID2);
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getAll() throws Exception {
//        assertEquals(getExpectedMastersLazy(), testGetPagedModel(makeUri(ENDPOINT), MASTER_PAGE));
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getAllChatIds() throws Exception {
//
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getAllWithComposedObjectLinksTested() throws Exception {
//        final List<Master> expected = getExpectedMastersEager();
//        AssociatedObject[] associatedObjects = {new AssociatedObject("ghbUser", USER_MODEL)};
//        final List<Master> result = testGetPagedModel(makeUri(ENDPOINT), MASTER_PAGE, associatedObjects);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getAllEager() throws Exception {
//        final List<Master> expected = getExpectedMastersEager();
//        final List<Master> result = testGetPagedModel(makeUri(makeQuery("projection", "full"), ENDPOINT), MASTER_PAGE);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getById() throws Exception {
//        Master result = testGetEntityModel(makeUri(ENDPOINT, MASTER_ID1), MASTER_MODEL);
//        assertEquals(getExpectedMasterLazy(MASTER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getByIdWithComposedObjectLinksTested() throws Exception {
//        AssociatedObject[] associatedObjects = {new AssociatedObject("ghbUser", USER_MODEL)};
//        final Master result = testGetEntityModel(makeUri(ENDPOINT, MASTER_ID1), MASTER_MODEL, associatedObjects);
//        assertEquals(getExpectedMasterEager(MASTER_ID1, USER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getByIdEager() throws Exception {
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, MASTER_ID1), MASTER_MODEL);
//        assertEquals(getExpectedMasterEager(MASTER_ID1, USER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void getByTelegramId() throws Exception {
//        Master result = testGetEntityModel(makeUri(makeQuery("id", "telegram_chat_id1"), ENDPOINT, "search", "findByGhbUser_TelegramChatId"), MASTER_MODEL);
//        assertEquals(getExpectedMasterLazy(MASTER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void save() throws Exception {
//        final EntityModel<GhbUser> ghbUserLink = doGetForModel(makeUri("ghbUsers", USER_ID1), new TypeReference<>() {
//        });
//        Master expected = new Master(null,
//                "name new",
//                "domain new",
//                "experience new",
//                "workplace new",
//                null);
//
//        String id = testPost(makeUri(ENDPOINT), expected);
//        testPutAssociation(makeUri(ENDPOINT, id, "ghbUser"), ghbUserLink.getRequiredLink("self").getHref());
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, id), MASTER_MODEL);
//
//        expected.setMasterId(UUID.fromString(id));
//        expected.setGhbUser(new GhbUser(USER_UUID1, "telegram_chat_id1", "name1"));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void update() throws Exception {
//        Master expected = new Master(MASTER_UUID1, "updated", null, null, null, null);
//        testPut(makeUri(ENDPOINT, MASTER_ID1), expected);
//
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, MASTER_ID1), MASTER_MODEL);
//        expected.setGhbUser(new GhbUser(USER_UUID1, "telegram_chat_id1", "name1"));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void updateDeleteAssociation() throws Exception {
//        testDelete(makeUri(ENDPOINT, MASTER_ID1, "ghbUser"));
//
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, MASTER_ID1), MASTER_MODEL);
//        assertEquals(getExpectedMasterLazy(MASTER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void change() throws Exception {
//        testPatch(makeUri(ENDPOINT, MASTER_ID1), new Master(MASTER_UUID1, "changed", null, null, null, null));
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, MASTER_ID1), MASTER_MODEL);
//
//        Master expected = getExpectedMasterEager(MASTER_ID1, USER_ID1, 1);
//        expected.setName("changed");
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void changeAssociation() throws Exception {
//        final EntityModel<GhbUser> ghbUserLink = doGetForModel(makeUri("ghbUsers", USER_ID2), new TypeReference<>() {
//        });
//        testPutAssociation(makeUri(ENDPOINT, MASTER_ID1, "ghbUser"), ghbUserLink.getRequiredLink("self").getHref());
//
//        Master result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, MASTER_ID1), MASTER_MODEL);
//
//        Master expected = getExpectedMasterLazy(MASTER_ID1, 1);
//        expected.setGhbUser(new GhbUser(USER_UUID2, "telegram_chat_id2", "name2"));
//        assertEquals(expected, result);
//    }
//
//
//    @Test
//    @Sql({"/user.sql", "/master.sql"})
//    @Transactional
//    void deleteById() throws Exception {
//        testDelete(makeUri(ENDPOINT, MASTER_ID1));
//    }
//
//    @NotNull
//    private List<Master> getExpectedMastersLazy() {
//        return List.of(
//                new Master(MASTER_UUID1, "name1", "domain1", "experience1", "workplace1", null),
//                new Master(MASTER_UUID2, "name2", "domain2", "experience2", "workplace2", null)
//        );
//    }
//
//    @NotNull
//    private List<Master> getExpectedMastersEager() {
//        return List.of(
//                new Master(MASTER_UUID1, "name1", "domain1", "experience1", "workplace1",
//                        new GhbUser(USER_UUID1, "telegram_chat_id1", "name1")),
//                new Master(MASTER_UUID2, "name2", "domain2", "experience2", "workplace2",
//                        new GhbUser(USER_UUID2, "telegram_chat_id2", "name2"))
//        );
//    }
//
//    @NotNull
//    private Master getExpectedMasterLazy(String uuid, int index) {
//        return new Master(UUID.fromString(uuid),
//                "name" + index,
//                "domain" + index,
//                "experience" + index,
//                "workplace" + index,
//                null);
//    }
//
//    @NotNull
//    private Master getExpectedMasterEager(String masterUuid, String ghbUserUuid, int index) {
//        Master master = getExpectedMasterLazy(masterUuid, index);
//        master.setGhbUser(new GhbUser(UUID.fromString(ghbUserUuid), "telegram_chat_id" + index, "name" + index));
//        return master;
//    }
//}
