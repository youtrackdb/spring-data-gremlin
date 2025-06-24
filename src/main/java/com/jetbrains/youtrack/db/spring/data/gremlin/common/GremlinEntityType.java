/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common;

import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSource;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceEdge;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceGraph;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import org.springframework.lang.NonNull;

import java.util.function.Supplier;

public enum GremlinEntityType {

    VERTEX(GremlinSourceVertex::new),
    EDGE(GremlinSourceEdge::new),
    GRAPH(GremlinSourceGraph::new);

    private Supplier<GremlinSource> creator;

    GremlinEntityType(@NonNull Supplier<GremlinSource> creator) {
        this.creator = creator;
    }

    public GremlinSource createGremlinSource() {
        return this.creator.get();
    }
}
