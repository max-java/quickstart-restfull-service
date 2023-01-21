package com.tutrit.quickstart.restfull.servise.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static java.util.Collections.singletonList;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
public class Mappers {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper om = new ObjectMapper();
        //Don't fail if additional fields in incoming JSON, just ignore
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //Don't fail on incoming JSON missing fields
        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //Don't include null fields into json (need for patch methods tests)
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.registerModule(new Jackson2HalModule());
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return om;
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(singletonList(HAL_JSON));
        converter.setObjectMapper(objectMapper());

        return converter;
    }
}
