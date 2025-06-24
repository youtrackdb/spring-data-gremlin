/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.query.query;

import java.util.List;

public interface QueryScriptGenerator {

    List<String> generate(GremlinQuery query);
}
