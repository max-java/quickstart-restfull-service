package com.tutrit.quickstart.restfull.servise.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.quickstart.restfull.servise.util.AssociatedObject;
import com.tutrit.quickstart.restfull.servise.util.HateoasUtil;
import com.tutrit.quickstart.restfull.servise.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    protected URI makeUri(@Nullable MultiValueMap<String, String> params, String... pathSegments) {
        return UriComponentsBuilder.newInstance()
                .pathSegment(pathSegments)
                .queryParams(params)
                .build()
                .toUri();
    }

    protected URI makeUri(@NotNull String... pathSegments) {
        return makeUri(null, pathSegments);
    }

    protected MultiValueMap<String, String> makeQuery(String... param) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (int i = 0; i < param.length; i += 2) {
            params.add(param[i], param[i + 1]);
        }
        return params;
    }

    protected <T> T testGetEntityModel(URI uri,
                                       TypeReference<EntityModel<T>> typeReference,
                                       AssociatedObject... associatedObjects) throws Exception {
        EntityModel<T> entityModel = testGet(uri, typeReference);
        if (associatedObjects != null) {
            Stream.of(associatedObjects)
                    .forEach(associatedObj -> ReflectionUtil.setFieldValueSneakyThrow(
                            entityModel.getContent(),
                            associatedObj.fieldName,
                            testGetSneakyThrow(HateoasUtil.extractHrefAsUri(entityModel, associatedObj.fieldName), associatedObj.typeReference)));
        }
        return entityModel.getContent();
    }

    /**
     * If root object has associated objects (@OneToOne, @OneToMany, @ManyToOne), and this associated object has
     * defined repository rest resource, Spring renders associated object attribute as a URI to its corresponding domain object resource.
     * If this test method contains `associatedObjects` param, it tests URI of every associated objects and sets result ot root object
     * at EntityModel level
     *
     * @param uri
     * @param typeReference
     * @param associatedObjects pair of composed object field name and type
     * @param <T>
     * @return
     * @throws Exception
     */
    protected <T> List<T> testGetPagedModel(URI uri,
                                            TypeReference<PagedModel<EntityModel<T>>> typeReference,
                                            AssociatedObject... associatedObjects) throws Exception {
        final PagedModel<EntityModel<T>> paged = testGet(uri, typeReference);
        if (associatedObjects == null) {
            return paged.getContent().stream().map(e -> e.getContent()).collect(Collectors.toList());
        }
        return paged.getContent().stream()
                .map(e -> {
                    Stream.of(associatedObjects)
                            .forEach(associatedObj -> ReflectionUtil.setFieldValueSneakyThrow(
                                    e.getContent(),
                                    associatedObj.fieldName,
                                    testGetSneakyThrow(HateoasUtil.extractHrefAsUri(e, associatedObj.fieldName), associatedObj.typeReference)));
                    return e;
                })
                .map(e -> e.getContent())
                .collect(Collectors.toList());
    }

    protected <T> T testGet(URI uri, TypeReference<T> valueTypeRef) throws Exception {
        MvcResult mvcResult =
                mockMvc
                        .perform(get(uri))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), valueTypeRef);
    }

    protected <T> T testGet(URI uri, Class<T> clazz) throws Exception {
        MvcResult mvcResult =
                mockMvc
                        .perform(get(uri))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }

    protected <T> T testGetSneakyThrow(URI uri, TypeReference<T> valueTypeRef) {
        try {
            return testGet(uri, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String testPost(URI uri, Object obj) throws Exception {
        MvcResult mvcResult =
                mockMvc
                        .perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(obj)))
                        .andExpect(status().isCreated())
                        .andReturn();
        String location = mvcResult.getResponse().getHeaderValue("Location").toString();
        return location.substring(location.indexOf(uri.toString()) + uri.toString().length() + 1);
    }

    protected void testPut(URI uri, Object obj) throws Exception {
        mockMvc
                .perform(put(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(obj)))
                .andExpect(status().isNoContent());
    }

    protected void testPutAssociation(URI uri, String associatedObjLink) throws Exception {
        mockMvc
                .perform(put(uri)
                        .contentType("text/uri-list")
                        .content(associatedObjLink))
                .andExpect(status().isNoContent());
    }

    protected void testPatchAssociation(URI uri, String associatedObjLink) throws Exception {
        mockMvc
                .perform(patch(uri)
                        .contentType("text/uri-list")
                        .content(associatedObjLink))
                .andExpect(status().isNoContent());
    }

    protected void testPatch(URI uri, Object obj) throws Exception {
        mockMvc
                .perform(patch(uri).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(obj)))
                .andExpect(status().isNoContent());
    }

    protected void testDelete(final URI uri) throws Exception {
        mockMvc
                .perform(delete(uri))
                .andExpect(status().isNoContent());
        mockMvc
                .perform(get(uri))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    protected <T> EntityModel<T> doGetForModel(URI uri, TypeReference<EntityModel<T>> typeReference) throws Exception {
        MvcResult mvcResult =
                mockMvc
                        .perform(get(uri))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), typeReference);
    }

    abstract void getAll() throws Exception;

    abstract void getById() throws Exception;

    abstract void save() throws Exception;

    abstract void update() throws Exception;

    abstract void change() throws Exception;

    abstract void deleteById() throws Exception;
}
