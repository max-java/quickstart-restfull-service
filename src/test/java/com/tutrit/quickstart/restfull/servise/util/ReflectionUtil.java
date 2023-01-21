package com.tutrit.quickstart.restfull.servise.util;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class ReflectionUtil {

    public static Field setFieldValue(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (value instanceof EntityModel) {
            value = ((EntityModel<?>) value).getContent();
        }
        if (value instanceof PagedModel) {
            value = ((PagedModel<EntityModel>) value).getContent().stream()
                    .map(entityModel -> entityModel.getContent())
                    .collect(Collectors.toList());
        }
        var field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        field.set(obj, value);
        return field;
    }

    public static Field setFieldValueSneakyThrow(Object obj, String fieldName, Object value) {
        try {
            return setFieldValue(obj, fieldName, value);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object getPrivateFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        var field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            return superClass == null ? throwException(e) : getField(superClass, fieldName);
        }
    }

    public static Field throwException(NoSuchFieldException e) throws NoSuchFieldException {
        throw e;
    }
}
