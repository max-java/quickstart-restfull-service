package com.tutrit.quickstart.restfull.servise.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tutrit.quickstart.restfull.servise.util.AssociatedObject;
import com.tutrit.quickstart.restfull.servise.util.TypeRefOf;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import com.tutrit.quickstart.restfull.servise.bean.Customer;
import com.tutrit.quickstart.restfull.servise.bean.Issue;
import com.tutrit.quickstart.restfull.servise.bean.IssueStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IssueControllerTest extends BaseControllerTest {
    private final String ENDPOINT = "issues";
    private final String ISSUE_ID1 = "f8cf1713-e411-4558-af76-0b0d6129fef1";
    private final String ISSUE_ID2 = "f8cf1713-e411-4558-af76-0b0d6129fef2";
    private final String ISSUE_ID3 = "f8cf1713-e411-4558-af76-0b0d6129fef3";
    private final String ISSUE_ID4 = "f8cf1713-e411-4558-af76-0b0d6129fef4";
    private final String CUSTOMER_ID1 = "14ded5c9-2b65-41c8-82ec-ad6ac17e2f51";
    private final String CUSTOMER_ID2 = "24ded5c9-2b65-41c8-82ec-ad6ac17e2f52";

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getAll() throws Exception {
        assertEquals(getExpectedIssuesLazy(), testGetPagedModel(makeUri(ENDPOINT), TypeRefOf.ISSUE_PAGE));
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getAllWithComposedObjectLinksTested() throws Exception {
        final List<Issue> expected = getExpectedIssuesEager();
        AssociatedObject[] associatedObjects = {new AssociatedObject("customer", TypeRefOf.CUSTOMER_MODEL)};
        final List<Issue> result = testGetPagedModel(makeUri(ENDPOINT), TypeRefOf.ISSUE_PAGE, associatedObjects);
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getAllEager() throws Exception {
        final List<Issue> expected = getExpectedIssuesEager();
        final List<Issue> result = testGetPagedModel(makeUri(makeQuery("projection", "full"), ENDPOINT), TypeRefOf.ISSUE_PAGE);
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getById() throws Exception {
        final Issue result = testGetEntityModel(makeUri(ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);
        assertEquals(getExpectedIssueLazy(ISSUE_ID1, 1), result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getByIdWithComposedObjectLinksTested() throws Exception {
        AssociatedObject[] associatedObjects = {new AssociatedObject("customer", TypeRefOf.CUSTOMER_MODEL)};
        final Issue result = testGetEntityModel(makeUri(ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL, associatedObjects);
        assertEquals(getExpectedIssueEager(ISSUE_ID1, 1, CUSTOMER_ID1, 1), result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void getByIdEager() throws Exception {
        final Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);
        assertEquals(getExpectedIssueEager(ISSUE_ID1, 1, CUSTOMER_ID1, 1), result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void save() throws Exception {
        final String customerLink = doGetForModel(makeUri("customers", CUSTOMER_ID1), TypeRefOf.CUSTOMER_MODEL)
                .getRequiredLink("self")
                .getHref();
        Issue expected = new Issue(null,
                "description new",
                "photo new",
                "city new",
                IssueStatus.NEW,
                LocalDateTime.parse("2022-02-02T00:00"),
                null);

        String id = testPost(makeUri(ENDPOINT), expected);
        testPutAssociation(makeUri(ENDPOINT, id, "customer"), customerLink);
        Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, id), TypeRefOf.ISSUE_MODEL);

        expected.setIssueId(UUID.fromString(id));
        expected.setCustomer(makeCustomer(CUSTOMER_ID1, 1));
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void update() throws Exception {
        Issue expected = new Issue(UUID.fromString(ISSUE_ID1), "updated", null, null, null, null, null); // TODO: 8/7/22 fix tests
        testPut(makeUri(ENDPOINT, ISSUE_ID1), expected);

        Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);
        expected.setCustomer(makeCustomer(CUSTOMER_ID1, 1));
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void updateDeleteAssociation() throws Exception {
        testDelete(makeUri(ENDPOINT, ISSUE_ID1, "customer"));

        Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);
        assertEquals(getExpectedIssueLazy(ISSUE_ID1, 1), result);

        assertNotNull(testGetEntityModel(makeUri("customers", CUSTOMER_ID1), TypeRefOf.CUSTOMER_MODEL));
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void change() throws Exception {
        testPatch(makeUri(ENDPOINT, ISSUE_ID1), new Issue(UUID.fromString(ISSUE_ID1), "changed", null, null, null, null, null)); // TODO: 8/7/22 fix tests
        Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);

        Issue expected = getExpectedIssueEager(ISSUE_ID1, 1, CUSTOMER_ID1, 1);
        expected.setDescription("changed");
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void changeAssociation() throws Exception {
        final EntityModel<Customer> customerLink = doGetForModel(makeUri("customers", CUSTOMER_ID2), new TypeReference<>() {
        });
        testPutAssociation(makeUri(ENDPOINT, ISSUE_ID1, "customer"), customerLink.getRequiredLink("self").getHref());

        Issue result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, ISSUE_ID1), TypeRefOf.ISSUE_MODEL);

        Issue expected = getExpectedIssueLazy(ISSUE_ID1, 1);
        expected.setCustomer(makeCustomer(CUSTOMER_ID2, 2));
        assertEquals(expected, result);
    }

    @Test
    @Sql({"/user.sql", "/customer.sql", "/issue.sql"})
    @Transactional
    void deleteById() throws Exception {
        testDelete(makeUri(ENDPOINT, ISSUE_ID1));
    }


    private List<Issue> getExpectedIssuesLazy() {
        return List.of(
                getExpectedIssueLazy(ISSUE_ID1, 1),
                getExpectedIssueLazy(ISSUE_ID2, 2),
                getExpectedIssueLazy(ISSUE_ID3, 3),
                getExpectedIssueLazy(ISSUE_ID4, 4)
        );
    }

    private List<Issue> getExpectedIssuesEager() {
        return List.of(
                getExpectedIssueEager(ISSUE_ID1, 1, CUSTOMER_ID1, 1),
                getExpectedIssueEager(ISSUE_ID2, 2, CUSTOMER_ID1, 1),
                getExpectedIssueEager(ISSUE_ID3, 3, CUSTOMER_ID2, 2),
                getExpectedIssueEager(ISSUE_ID4, 4, CUSTOMER_ID2, 2)
        );
    }

    @NotNull
    private Issue getExpectedIssueLazy(String uuid, int index) {
        return new Issue(UUID.fromString(uuid),
                "description" + index,
                "photo" + index,
                "city" + index,
                IssueStatus.NEW,
                LocalDateTime.parse("2022-02-02T00:00"),
                null);
    }

    private Issue getExpectedIssueEager(String issueId, int issueIndex, String customerId, int customerIndex) {
        Issue issue = getExpectedIssueLazy(issueId, issueIndex);
        issue.setCustomer(makeCustomer(customerId, customerIndex));
        return issue;
    }

    private Customer makeCustomer(String customerId, int customerIndex) {
        return new Customer(
                UUID.fromString(customerId),
                "name" + customerIndex,
                "phone" + customerIndex,
                "ownerName" + customerIndex,
                null,
                null);
    }
}
