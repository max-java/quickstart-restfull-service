//package com.tutrit.quickstart.restapi.controller;
//
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//import com.tutrit.quickstart.restapi.bean.Customer;
//import com.tutrit.quickstart.restapi.bean.GhbUser;
//import com.tutrit.quickstart.restapi.bean.Issue;
//import com.tutrit.quickstart.restapi.util.AssociatedObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.CUSTOMER_MODEL;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.CUSTOMER_PAGE;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.ISSUE_MODEL;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.ISSUE_PAGE;
//import static com.tutrit.quickstart.restapi.util.TypeRefOf.USER_MODEL;
//
//public class CustomerControllerTest extends BaseControllerTest {
//
//    private final String ENDPOINT = "customers";
//    private final String CUSTOMER_ID1 = "14ded5c9-2b65-41c8-82ec-ad6ac17e2f51";
//    private final String CUSTOMER_ID2 = "24ded5c9-2b65-41c8-82ec-ad6ac17e2f52";
//    private final String USER_ID1 = "f8cf1713-e411-4558-af76-0b0d6129fef1";
//    private final String USER_ID2 = "f8cf1713-e411-4558-af76-0b0d6129fef2";
//    private final String ISSUE_ID1 = "f8cf1713-e411-4558-af76-0b0d6129fef1";
//    private final String ISSUE_ID2 = "f8cf1713-e411-4558-af76-0b0d6129fef2";
//    private final String ISSUE_ID3 = "f8cf1713-e411-4558-af76-0b0d6129fef3";
//    private final String ISSUE_ID4 = "f8cf1713-e411-4558-af76-0b0d6129fef4";
//
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql"})
//    @Transactional
//    void getAll() throws Exception {
//        assertEquals(getExpectedCustomersLazy(), testGetPagedModel(makeUri(ENDPOINT), CUSTOMER_PAGE));
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void getAllWithComposedObjectLinksTested() throws Exception {
//        final List<Customer> expected = getExpectedCustomersEager();
//        AssociatedObject[] associatedObjects = {
//                new AssociatedObject("ghbUser", USER_MODEL),
//                new AssociatedObject("issues", ISSUE_PAGE),
//        };
//        final List<Customer> result = testGetPagedModel(makeUri(ENDPOINT), CUSTOMER_PAGE, associatedObjects);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void getAllEager() throws Exception {
//        final List<Customer> expected = getExpectedCustomersEager();
//        final List<Customer> result = testGetPagedModel(makeUri(makeQuery("projection", "full"), ENDPOINT), CUSTOMER_PAGE);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void getById() throws Exception {
//        Customer result = testGetEntityModel(makeUri(ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//        assertEquals(getExpectedCustomerLazy(CUSTOMER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql"})
//    @Transactional
//    void getByTelegramId() throws Exception {
//        Customer result = testGetEntityModel(makeUri(makeQuery("id", "telegram_chat_id1"), ENDPOINT, "search", "findByGhbUser_TelegramChatId"), CUSTOMER_MODEL);
//        assertEquals(getExpectedCustomerLazy(CUSTOMER_ID1, 1), result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void getByIdWithComposedObjectLinksTested() throws Exception {
//        AssociatedObject[] associatedObjects = {
//                new AssociatedObject("ghbUser", USER_MODEL),
//                new AssociatedObject("issues", ISSUE_PAGE),
//        };
//        final Customer result = testGetEntityModel(makeUri(ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL, associatedObjects);
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//        setIssue(expected, ISSUE_ID1, 1);
//        setIssue(expected, ISSUE_ID2, 2);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void getByIdEager() throws Exception {
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//        setIssue(expected, ISSUE_ID1, 1);
//        setIssue(expected, ISSUE_ID2, 2);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void save() throws Exception {
//        final String ghbUserLink = doGetForModel(makeUri("ghbUsers", USER_ID1), USER_MODEL)
//                .getRequiredLink("self")
//                .getHref();
//        Customer expected = new Customer(null,
//                "name new",
//                "phone new",
//                "ownerName new",
//                null,
//                null);
//
//        String id = testPost(makeUri(ENDPOINT), expected);
//        testPutAssociation(makeUri(ENDPOINT, id, "ghbUser"), ghbUserLink);
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, id), CUSTOMER_MODEL);
//
//        expected.setCustomerId(UUID.fromString(id));
//        expected.setGhbUser(makeUser(USER_ID1, 1));
//        expected.setIssues(null);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void update() throws Exception {
//        Customer expected = new Customer(UUID.fromString(CUSTOMER_ID1), "updated", null, null, null, null);
//        testPut(makeUri(ENDPOINT, CUSTOMER_ID1), expected);
//
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//        expected.setGhbUser(makeUser(USER_ID1, 1));
//        expected.setIssues(List.of(
//                makeIssue(ISSUE_ID1, 1),
//                makeIssue(ISSUE_ID2, 2)));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql", "/master.sql"})
//    @Transactional
//    void updateDeleteAssociation() throws Exception {
//        testDelete(makeUri(ENDPOINT, CUSTOMER_ID1, "ghbUser"));
//        testPutAssociation(makeUri(ENDPOINT, CUSTOMER_ID1, "issues"), "");
//
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//        Customer expected = getExpectedCustomerLazy(CUSTOMER_ID1, 1);
//        expected.setIssues(new ArrayList<>());
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void change() throws Exception {
//        testPatch(makeUri(ENDPOINT, CUSTOMER_ID1), new Customer(UUID.fromString(CUSTOMER_ID1), "changed", null, null, null, null));
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//
//        expected.setName("changed");
//        expected.setIssues(List.of(
//                makeIssue(ISSUE_ID1, 1),
//                makeIssue(ISSUE_ID2, 2)));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void changeAssociation() throws Exception {
//        final String ghbUserLink = doGetForModel(makeUri("ghbUsers", USER_ID2), USER_MODEL)
//                .getRequiredLink("self")
//                .getHref();
//        testPutAssociation(makeUri(ENDPOINT, CUSTOMER_ID1, "ghbUser"), ghbUserLink);
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//
//        Customer expected = getExpectedCustomerLazy(CUSTOMER_ID1, 1);
//        expected.setGhbUser(makeUser(USER_ID2, 2));
//        expected.setIssues(List.of(
//                makeIssue(ISSUE_ID1, 1),
//                makeIssue(ISSUE_ID2, 2)));
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void changeAssociation_replaceIssueList() throws Exception {
//        final String issueLink = doGetForModel(makeUri("issues", ISSUE_ID3), ISSUE_MODEL)
//                .getRequiredLink("self")
//                .getHref();
//        testPutAssociation(makeUri(ENDPOINT, CUSTOMER_ID1, "issues"), issueLink);
//
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//        expected.setIssues(List.of(makeIssue(ISSUE_ID3, 3)));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void changeAssociation_addIssueToList() throws Exception {
//        final String issueLink = doGetForModel(makeUri("issues", ISSUE_ID3), ISSUE_MODEL)
//                .getRequiredLink("self")
//                .getHref();
//        testPatchAssociation(makeUri(ENDPOINT, CUSTOMER_ID1, "issues"), issueLink);
//
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//        expected.setIssues(List.of(
//                makeIssue(ISSUE_ID1, 1),
//                makeIssue(ISSUE_ID2, 2),
//                makeIssue(ISSUE_ID3, 3)));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void changeAssociation_removeIssueFromList() throws Exception {
//        testDelete(makeUri(ENDPOINT, CUSTOMER_ID1, "issues", ISSUE_ID1));
//        Customer result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, CUSTOMER_ID1), CUSTOMER_MODEL);
//
//        Customer expected = getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1);
//        expected.setIssues(List.of(makeIssue(ISSUE_ID2, 2)));
//        assertEquals(expected, result);
//    }
//
//
//    @Test
//    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
//    @Transactional
//    void deleteById() throws Exception {
//        testDelete(makeUri(ENDPOINT, CUSTOMER_ID1));
//    }
//
//    @NotNull
//    private List<Customer> getExpectedCustomersLazy() {
//        return List.of(
//                getExpectedCustomerLazy(CUSTOMER_ID1, 1),
//                getExpectedCustomerLazy(CUSTOMER_ID2, 2)
//        );
//    }
//
//    private List<Customer> getExpectedCustomersEager() {
//        final List<Customer> customers = List.of(
//                getExpectedCustomerEager(CUSTOMER_ID1, 1, USER_ID1, 1),
//                getExpectedCustomerEager(CUSTOMER_ID2, 2, USER_ID2, 2)
//        );
//        customers.get(0).setIssues(List.of(makeIssue(ISSUE_ID1, 1), makeIssue(ISSUE_ID2, 2)));
//        customers.get(1).setIssues(List.of(makeIssue(ISSUE_ID3, 3), makeIssue(ISSUE_ID4, 4)));
//        return customers;
//    }
//
//    private Customer getExpectedCustomerLazy(String customerId, int customerIndex) {
//        return new Customer(
//                UUID.fromString(customerId),
//                "name" + customerIndex,
//                "phone" + customerIndex,
//                "ownerName" + customerIndex,
//                null,
//                null);
//    }
//
//    private GhbUser makeUser(String uuid, int index) {
////        return new GhbUser(UUID.fromString(uuid), "telegram_chat_id" + index, "name" + index);
//    }
//
//    private Issue makeIssue(String uuid, int index) {
//        return new Issue(UUID.fromString(uuid),
//                "description" + index,
//                "photo" + index,
//                null);
//    }
//
//
//    private Customer getExpectedCustomerEager(String customerUuid, int customerIndex, String ghbUserId, int ghbUserIndex) {
//        Customer customer = getExpectedCustomerLazy(customerUuid, customerIndex);
//        customer.setGhbUser(makeUser(ghbUserId, ghbUserIndex));
//        return customer;
//    }
//
//    private Customer setIssue(Customer customer, String issueUuid, int issueIndex) {
//        if (customer.getIssues() == null) {
//            customer.setIssues(new ArrayList<>());
//        }
//        customer.getIssues().add(makeIssue(issueUuid, issueIndex));
//        return customer;
//    }
//}
