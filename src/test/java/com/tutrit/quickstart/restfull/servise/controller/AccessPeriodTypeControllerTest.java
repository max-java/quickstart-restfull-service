package com.tutrit.quickstart.restfull.servise.controller;

import com.tutrit.quickstart.restfull.servise.util.TypeRefOf;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import com.tutrit.quickstart.restfull.servise.bean.AccessPeriod;
import com.tutrit.quickstart.restfull.servise.bean.Master;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessPeriodTypeControllerTest extends BaseControllerTest {
    private final String MASTER_ID1 = "14ded5c9-2b65-41c8-82ec-ad6ac17e2f51";
    private final String ENDPOINT = "accessPeriods";

    @Override
    void getAll() throws Exception {

    }

    @Override
    void getById() throws Exception {

    }

    @Test
    @Sql({"/user.sql", "/master.sql"})
    @Transactional
    void save() throws Exception {
        final String masterLink = doGetForModel(makeUri("masters", MASTER_ID1), TypeRefOf.MASTER_MODEL)
                .getRequiredLink("self")
                .getHref();
        AccessPeriod expected = new AccessPeriod(null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);

        String id = testPost(makeUri(ENDPOINT), expected);
        testPutAssociation(makeUri(ENDPOINT, id, "master"), masterLink);
        AccessPeriod result = testGetEntityModel(makeUri(makeQuery("projection", "full"), ENDPOINT, id), TypeRefOf.ACCESS_PERIOD_MODEL);

        expected.setAccessPeriodId(UUID.fromString(id));
        expected.setMaster(makeMaster(MASTER_ID1, 1));
        assertEquals(expected, result);
    }

    @Override
    void update() throws Exception {

    }

    @Override
    void change() throws Exception {

    }

    @Override
    void deleteById() throws Exception {

    }

    private Master makeMaster(String masterId, int masterIndex) {
        return new Master(
                UUID.fromString(masterId),
                "name" + masterIndex,
                "photo",
                "domain" + masterIndex,
                "experience" + masterIndex,
                "workplace" + masterIndex,
                null,
                null
        );
    }
}
