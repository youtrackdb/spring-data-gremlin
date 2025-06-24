/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.script;

import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSource;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceGraph;
import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;

import static com.jetbrains.youtrack.db.spring.data.gremlin.common.Constants.*;

@NoArgsConstructor
public class GremlinScriptLiteralGraph implements GremlinScriptLiteral {

    private final GremlinScriptLiteralVertex scriptVertex = new GremlinScriptLiteralVertex();

    private final GremlinScriptLiteralEdge scriptEdge = new GremlinScriptLiteralEdge();

    @Override
    public List<String> generateInsertScript(@NonNull GremlinSource source) {
        return generateInsertUpdateScript(source,
                scriptVertex::generateInsertScript,
                scriptEdge::generateInsertScript);
    }

    @Override
    public List<String> generateDeleteAllScript() {
        return Arrays.asList(GREMLIN_SCRIPT_EDGE_DROP_ALL, GREMLIN_QUERY_BARRIER, GREMLIN_SCRIPT_VERTEX_DROP_ALL);
    }

    @Override
    public List<String> generateDeleteAllByClassScript(@NonNull GremlinSource source) {
        return generateDeleteAllScript();
    }

    @Override
    public List<String> generateFindByIdScript(@Nullable GremlinSource source) {
        throw new UnsupportedOperationException("Gremlin graph cannot findById by single query.");
    }

    @Override
    public List<String> generateUpdateScript(@NonNull GremlinSource source) {
        return generateInsertUpdateScript(source,
                scriptVertex::generateUpdateScript,
                scriptEdge::generateUpdateScript);
    }

    @SuppressWarnings("unchecked")
    private List<String> generateInsertUpdateScript(@NonNull GremlinSource source,
                                                    @NonNull Function<GremlinSource, List<String>> vertexHandler,
                                                    @NonNull Function<GremlinSource, List<String>> edgeHandler) {
        if (!(source instanceof GremlinSourceGraph)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceGraph");
        }

        final List<String> scriptList = new ArrayList<>();
        final GremlinSourceGraph sourceGraph = (GremlinSourceGraph) source;
        final List<GremlinSource> vertexes = (List<GremlinSource>) sourceGraph.getVertexSet();
        final List<GremlinSource> edges = (List<GremlinSource>) sourceGraph.getEdgeSet();

        vertexes.forEach(vertex -> scriptList.addAll(vertexHandler.apply(vertex)));
        scriptList.add(GREMLIN_QUERY_BARRIER);
        edges.forEach(edge -> scriptList.addAll(edgeHandler.apply(edge)));

        return scriptList;
    }

    @Override
    public List<String> generateDeleteByIdScript(@NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceGraph)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceGraph");
        }

        return this.generateDeleteAllScript();
    }

    @Override
    public List<String> generateFindAllScript(@NonNull GremlinSource source) {
        throw new UnsupportedOperationException("Gremlin graph cannot be findAll.");
    }

    public List<String> generateIsEmptyScript() {
        final List<String> scriptList = Arrays.asList(GREMLIN_PRIMITIVE_GRAPH, GREMLIN_PRIMITIVE_VERTEX_ALL);
        final String query = String.join(GREMLIN_PRIMITIVE_INVOKE, scriptList);

        return Collections.singletonList(query);
    }

    @Override
    public List<String> generateCountScript(@NonNull GremlinSource source) {
        throw new UnsupportedOperationException("Gremlin graph counting is not available.");
    }
}
