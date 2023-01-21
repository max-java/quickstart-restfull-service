package com.tutrit.quickstart.restfull.servise.config;

import com.tutrit.quickstart.restfull.servise.bean.AccessPeriod;
import com.tutrit.quickstart.restfull.servise.bean.Customer;
import com.tutrit.quickstart.restfull.servise.bean.GhbUser;
import com.tutrit.quickstart.restfull.servise.bean.Issue;
import com.tutrit.quickstart.restfull.servise.bean.Master;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class JsonModel {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(GhbUser.class);
            config.exposeIdsFor(Master.class);
            config.exposeIdsFor(Issue.class);
            config.exposeIdsFor(Customer.class);
            config.exposeIdsFor(AccessPeriod.class);
        });
    }
}
