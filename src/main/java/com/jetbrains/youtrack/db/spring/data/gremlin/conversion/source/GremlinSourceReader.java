/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source;

import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.MappingGremlinConverter;

/**
 * Provider Entity type dependent read method.
 */
public interface GremlinSourceReader {
    /**
     * Read data from GremlinSource to domain
     */
    <T extends Object> T read(Class<T> domainClass, MappingGremlinConverter converter, GremlinSource<T> source);
}
