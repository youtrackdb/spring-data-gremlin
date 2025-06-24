/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.query.paramerter;


import org.springframework.data.repository.query.Parameters;

import java.util.List;

public class GremlinParameters extends Parameters<GremlinParameters, GremlinParameter> {
    public GremlinParameters(List<GremlinParameter> parameters) {
        super(parameters);
    }

    @Override
    protected GremlinParameters createFrom(List<GremlinParameter> parameters) {
        return new GremlinParameters(parameters);
    }
}
