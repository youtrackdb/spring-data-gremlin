/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.script;

import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSource;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jetbrains.youtrack.db.spring.data.gremlin.common.Constants.*;
import static com.jetbrains.youtrack.db.spring.data.gremlin.common.GremlinEntityType.VERTEX;

@NoArgsConstructor
public class GremlinScriptLiteralVertex extends AbstractGremlinScriptLiteral implements GremlinScriptLiteral {

    @Override
    @SuppressWarnings("unchecked")
    public List<String> generateInsertScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        final List<String> scriptList = new ArrayList<>();

        scriptList.add(GREMLIN_PRIMITIVE_GRAPH);                                            // g
        scriptList.add(generateAddEntityWithLabel(source.getLabel(), VERTEX));              // addV('label')

        source.getId().ifPresent(id -> scriptList.add(generatePropertyWithRequiredId(id))); // property(id, xxx)

        scriptList.addAll(generateProperties(source.getProperties()));

        return completeScript(scriptList);
    }

    @Override
    public List<String> generateDeleteAllScript() {
        return Collections.singletonList(GREMLIN_SCRIPT_VERTEX_DROP_ALL);
    }

    @Override
    public List<String> generateDeleteAllByClassScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        final List<String> scriptList = Arrays.asList(
                GREMLIN_PRIMITIVE_GRAPH,             // g
                GREMLIN_PRIMITIVE_VERTEX_ALL,        // V()
                generateHasLabel(source.getLabel()), // has(label, 'label')
                GREMLIN_PRIMITIVE_DROP               // drop()
        );

        return completeScript(scriptList);
    }

    @Override
    public List<String> generateFindByIdScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        Assert.isTrue(source.getId().isPresent(), "GremlinSource should contain id.");

        final List<String> scriptList = Arrays.asList(
                GREMLIN_PRIMITIVE_GRAPH,                                 // g
                GREMLIN_PRIMITIVE_VERTEX_ALL,                            // V()
                generateHasId(source.getId().get(), source.getIdField()) // hasId(xxx)
        );

        return completeScript(scriptList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> generateUpdateScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        final List<String> scriptList = new ArrayList<>();

        Assert.isTrue(source.getId().isPresent(), "GremlinSource should contain id.");

        scriptList.add(GREMLIN_PRIMITIVE_GRAPH);                                    // g
        scriptList.add(generateEntityWithRequiredId(source.getId().get(), VERTEX)); // V(id)
        scriptList.addAll(generateProperties(source.getProperties()));

        return completeScript(scriptList);
    }

    @Override
    public List<String> generateFindAllScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        final String classname = source.getProperties().get(GREMLIN_PROPERTY_CLASSNAME).toString();
        Assert.notNull(classname, "GremlinSource should contain predefined classname");

        final List<String> scriptList = Arrays.asList(
                GREMLIN_PRIMITIVE_GRAPH,                           // g
                GREMLIN_PRIMITIVE_VERTEX_ALL,                      // V()
                generateHasLabel(source.getLabel()),               // has(label, 'label')
                generateHas(GREMLIN_PROPERTY_CLASSNAME, classname) // has(_classname, 'xxxxxx')
        );

        return completeScript(scriptList);
    }

    @Override
    public List<String> generateDeleteByIdScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        Assert.isTrue(source.getId().isPresent(), "GremlinSource should contain id.");

        final List<String> scriptList = Arrays.asList(
                GREMLIN_PRIMITIVE_GRAPH,                                  // g
                GREMLIN_PRIMITIVE_VERTEX_ALL,                             // E()
                generateHasId(source.getId().get(), source.getIdField()), // hasId(xxx)
                GREMLIN_PRIMITIVE_DROP                                    // drop()
        );

        return completeScript(scriptList);
    }

    @Override
    public List<String> generateCountScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        return Collections.singletonList(GREMLIN_SCRIPT_VERTEX_ALL);
    }
}

