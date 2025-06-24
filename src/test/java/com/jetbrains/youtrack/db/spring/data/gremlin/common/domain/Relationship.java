/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.domain;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.Edge;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeFrom;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeTo;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.TestConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Edge(label = TestConstants.EDGE_RELATIONSHIP_LABEL)
public class Relationship {

    private String id;

    private String name;

    private String location;

    @EdgeFrom
    private Person person;

    @EdgeTo
    private Project project;
}
