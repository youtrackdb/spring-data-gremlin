/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.domain;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.Edge;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeFrom;
import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.EdgeTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Edge
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Neighbor {

    private Long id;

    private Long distance;

    @EdgeFrom
    private Student studentFrom;

    @EdgeTo
    private Student studentTo;
}
