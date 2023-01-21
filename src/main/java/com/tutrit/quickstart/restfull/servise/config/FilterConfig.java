package com.tutrit.quickstart.restfull.servise.config;

import com.tutrit.quickstart.restfull.servise.filter.IssueAccessFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<IssueAccessFilter> issueAccessFilter() {
        FilterRegistrationBean<IssueAccessFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IssueAccessFilter());

        registrationBean.addUrlPatterns("/issues/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
