/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.query.query;

import com.jetbrains.youtrack.db.spring.data.gremlin.mapping.GremlinPersistentProperty;
import com.jetbrains.youtrack.db.spring.data.gremlin.query.criteria.Criteria;
import com.jetbrains.youtrack.db.spring.data.gremlin.query.criteria.CriteriaType;
import com.jetbrains.youtrack.db.spring.data.gremlin.query.paramerter.GremlinParameterAccessor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.*;

public class GremlinQueryCreator extends AbstractQueryCreator<GremlinQuery, Criteria> {

    private final MappingContext<?, GremlinPersistentProperty> mappingContext;
    private static final Map<Part.Type, CriteriaType> criteriaMap;

    static {
        final Map<Part.Type, CriteriaType> map = new HashMap<>();

        map.put(Part.Type.AFTER, CriteriaType.AFTER);
        map.put(Part.Type.BEFORE, CriteriaType.BEFORE);
        map.put(Part.Type.BETWEEN, CriteriaType.BETWEEN);
        map.put(Part.Type.SIMPLE_PROPERTY, CriteriaType.IS_EQUAL);
        map.put(Part.Type.EXISTS, CriteriaType.EXISTS);

        criteriaMap = Collections.unmodifiableMap(map);
    }

    public GremlinQueryCreator(@NonNull PartTree partTree, @NonNull GremlinParameterAccessor accessor,
                               @NonNull MappingContext<?, GremlinPersistentProperty> mappingContext) {
        super(partTree, accessor);

        this.mappingContext = mappingContext;
    }

    @Override // Note (panli): side effect here, this method will change the iterator status of parameters.
    protected Criteria create(@NonNull Part part, @NonNull Iterator<Object> parameters) {
        final Part.Type type = part.getType();
        final String subject = this.mappingContext.getPersistentPropertyPath(part.getProperty()).toDotPath();
        final List<Object> values = new ArrayList<>();

        if (!criteriaMap.containsKey(type)) {
            throw new UnsupportedOperationException("Unsupported keyword: " + type.toString());
        }

        for (int i = 0; i < part.getNumberOfArguments(); i++) {
            Assert.isTrue(parameters.hasNext(), "should not reach the end of iterator");
            values.add(parameters.next());
        }

        return Criteria.getUnaryInstance(criteriaMap.get(type), subject, values);
    }

    @Override
    protected Criteria and(@NonNull Part part, @NonNull Criteria base, @NonNull Iterator<Object> parameters) {
        final Criteria right = this.create(part, parameters);

        return Criteria.getBinaryInstance(CriteriaType.AND, base, right);
    }

    @Override
    protected Criteria or(@NonNull Criteria base, @NonNull Criteria criteria) {
        return Criteria.getBinaryInstance(CriteriaType.OR, base, criteria);
    }

    @Override
    protected GremlinQuery complete(@NonNull Criteria criteria, @NonNull Sort sort) {
        return new GremlinQuery(criteria);
    }
}
