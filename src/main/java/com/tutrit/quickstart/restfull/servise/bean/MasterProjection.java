package com.tutrit.quickstart.restfull.servise.bean;

import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "full", types = {Master.class})
public interface MasterProjection {
    UUID getMasterId();

    String getName();

    String getDomain();

    String getExperience();

    String getWorkplace();

    GhbUser getGhbUser();
}
