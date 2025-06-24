/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.domain;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeSet;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.Graph;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.VertexSet;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Graph
public class Network {

    @Getter
    @Setter
    private String id;

    @Getter
    @VertexSet
    private List<Object> vertexList;

    @Getter
    @EdgeSet
    private List<Object> edgeList;

    public Network() {
        this.vertexList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public void vertexAdd(Object object) {
        this.vertexList.add(object);
    }

    public void edgeAdd(Object object) {
        this.edgeList.add(object);
    }
}
