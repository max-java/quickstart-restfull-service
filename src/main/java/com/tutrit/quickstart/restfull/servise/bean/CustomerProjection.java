package com.tutrit.quickstart.restfull.servise.bean;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.UUID;

@Projection(name = "full", types = {Customer.class})
public interface CustomerProjection {

    public UUID getCustomerId();

    public String getName();

    public String getPhone();

    public String getOwnerName();

    public GhbUser getGhbUser();

    public List<Issue> getIssues();
}
