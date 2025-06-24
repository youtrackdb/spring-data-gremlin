/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source;

import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.result.GremlinResultsGraphReader;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.result.GremlinResultsReader;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.script.GremlinScriptLiteralGraph;
import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GremlinSourceGraph<T> extends AbstractGremlinSource<T> {

    @Getter
    private List<GremlinSource> vertexSet = new ArrayList<>();

    @Getter
    private List<GremlinSource> edgeSet = new ArrayList<>();

    @Getter
    private GremlinResultsReader resultsReader;

    public GremlinSourceGraph() {
        super();
        initializeGremlinStrategy();
        this.setGremlinSourceReader(new GremlinSourceGraphReader());
        this.resultsReader = new GremlinResultsGraphReader();
    }

    public GremlinSourceGraph(Class<T> domainClass) {
        super(domainClass);
        initializeGremlinStrategy();
        this.setGremlinSourceReader(new GremlinSourceGraphReader());
        this.resultsReader = new GremlinResultsGraphReader();
    }

    public void addGremlinSource(GremlinSource source) {
        if (source instanceof GremlinSourceVertex) {
            this.vertexSet.add(source);
        } else if (source instanceof GremlinSourceEdge) {
            this.edgeSet.add(source);
        } else {
            throw new GremlinUnexpectedSourceTypeException("source type can only be Vertex or Edge");
        }
    }

    private void initializeGremlinStrategy() {
        this.setGremlinScriptStrategy(new GremlinScriptLiteralGraph());
        this.setGremlinSourceWriter(new GremlinSourceGraphWriter());
    }
}

