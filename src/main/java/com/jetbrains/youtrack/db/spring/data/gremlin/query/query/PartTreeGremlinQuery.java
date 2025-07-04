/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.query.query;

import com.jetbrains.youtrack.db.spring.data.gremlin.mapping.GremlinPersistentProperty;
import com.jetbrains.youtrack.db.spring.data.gremlin.query.GremlinOperations;
import com.jetbrains.youtrack.db.spring.data.gremlin.query.paramerter.GremlinParameterAccessor;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.lang.NonNull;

public class PartTreeGremlinQuery extends AbstractGremlinQuery {

    private final PartTree partTree;
    private final ResultProcessor processor;
    private final MappingContext<?, GremlinPersistentProperty> mappingContext;

    public PartTreeGremlinQuery(@NonNull GremlinQueryMethod method, @NonNull GremlinOperations operations) {
        super(method, operations);

        this.processor = method.getResultProcessor();
        this.partTree = new PartTree(method.getName(), processor.getReturnedType().getDomainType());
        this.mappingContext = operations.getMappingConverter().getMappingContext();
    }

    @Override
    protected GremlinQuery createQuery(@NonNull GremlinParameterAccessor accessor) {
        final GremlinQueryCreator creator = new GremlinQueryCreator(this.partTree, accessor, this.mappingContext);

        if (this.partTree.isLimiting()) {
            throw new UnsupportedOperationException("Limitation is not supported yet");
        }

        return creator.createQuery();
    }
}
