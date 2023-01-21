package com.tutrit.quickstart.restfull.servise.bean;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.UUID;

@Projection(name = "full", types = {AccessPeriod.class})
public interface AccessPeriodProjection {

    UUID getAccessPeriodId();

    LocalDateTime getFromDate();

    LocalDateTime getToDate();

    Master getMaster();
}
