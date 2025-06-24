/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeSet;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.VertexSet;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.Constants;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.GremlinUtils;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import com.jetbrains.youtrack.db.spring.data.gremlin.mapping.GremlinPersistentEntity;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;

@NoArgsConstructor
public class GremlinSourceGraphWriter implements GremlinSourceWriter {

    private void writeGraphSet(@NonNull List<Object> objectList, @NonNull MappingGremlinConverter mappingConverter,
                               @NonNull GremlinSourceGraph sourceGraph) {
        Assert.isInstanceOf(GremlinSourceGraph.class, sourceGraph, "should be instance of GremlinSourceGraph ");

        for (final Object object : objectList) {
            final GremlinSource source = GremlinUtils.toGremlinSource(object.getClass());

            source.doGremlinSourceWrite(object, mappingConverter);
            sourceGraph.addGremlinSource(source);
        }
    }

    @Override
    public void write(@NonNull Object domain, @NonNull MappingGremlinConverter converter,
                      @NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceGraph)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceEdge");
        }

        final GremlinSourceGraph sourceGraph = (GremlinSourceGraph) source;
        final GremlinPersistentEntity<?> persistentEntity = converter.getPersistentEntity(domain.getClass());
        final ConvertingPropertyAccessor accessor = converter.getPropertyAccessor(domain);

        for (final Field field : FieldUtils.getAllFields(domain.getClass())) {
            final PersistentProperty property = persistentEntity.getPersistentProperty(field.getName());
            if (property == null) {
                continue;
            }

            if (field.getName().equals(Constants.PROPERTY_ID) || field.getAnnotation(Id.class) != null) {
                continue;
            }

            @SuppressWarnings("unchecked") final List<Object> objects = (List<Object>) accessor.getProperty(property);

            if (field.getAnnotation(VertexSet.class) != null || field.getAnnotation(EdgeSet.class) != null) {
                this.writeGraphSet(objects, converter, sourceGraph);
            }
        }
    }
}

