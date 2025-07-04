/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.domain;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.Vertex;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.TestConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Vertex(label = TestConstants.VERTEX_LABEL)
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;

    private String name;
}
