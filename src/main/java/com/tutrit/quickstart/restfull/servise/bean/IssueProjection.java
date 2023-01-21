package com.tutrit.quickstart.restfull.servise.bean;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.UUID;

@Projection(name = "full", types = {Issue.class})
public interface IssueProjection {
    UUID getIssueId();

    String getDescription();

    String getCity();

    String getPhoto();

    Customer getCustomer();

    IssueStatus getIssueStatus();

    LocalDateTime getTimestamp();
}
