package com.tutrit.quickstart.restfull.servise.util;

import com.fasterxml.jackson.core.type.TypeReference;

public class AssociatedObject {
    public final String fieldName;
    public TypeReference typeReference;

    public <T> AssociatedObject(final String fieldName, final TypeReference<?> typeReference) {
        this.fieldName = fieldName;
        this.typeReference = typeReference;
    }
}
