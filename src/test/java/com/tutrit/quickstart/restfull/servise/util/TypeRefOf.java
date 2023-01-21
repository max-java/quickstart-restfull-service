package com.tutrit.quickstart.restfull.servise.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import com.tutrit.quickstart.restfull.servise.bean.Customer;
import com.tutrit.quickstart.restfull.servise.bean.GhbUser;
import com.tutrit.quickstart.restfull.servise.bean.Issue;
import com.tutrit.quickstart.restfull.servise.bean.Master;
import com.tutrit.quickstart.restfull.servise.bean.AccessPeriod;

public class TypeRefOf {
    public static final TypeReference<PagedModel<EntityModel<GhbUser>>> USER_PAGE = new TypeReference<>() {
    };
    public static final TypeReference<EntityModel<GhbUser>> USER_MODEL = new TypeReference<>() {
    };
    public static final TypeReference<PagedModel<EntityModel<Master>>> MASTER_PAGE = new TypeReference<>() {
    };
    public static final TypeReference<EntityModel<Master>> MASTER_MODEL = new TypeReference<>() {
    };
    public static final TypeReference<PagedModel<EntityModel<Issue>>> ISSUE_PAGE = new TypeReference<>() {
    };
    public static final TypeReference<EntityModel<Issue>> ISSUE_MODEL = new TypeReference<>() {
    };
    public static final TypeReference<PagedModel<EntityModel<Customer>>> CUSTOMER_PAGE = new TypeReference<>() {
    };
    public static final TypeReference<EntityModel<Customer>> CUSTOMER_MODEL = new TypeReference<>() {
    };
    public static final TypeReference<PagedModel<EntityModel<AccessPeriod>>> ACCESS_PERIOD_PAGE = new TypeReference<>() {
    };
    public static final TypeReference<EntityModel<AccessPeriod>> ACCESS_PERIOD_MODEL = new TypeReference<>() {
    };
}
