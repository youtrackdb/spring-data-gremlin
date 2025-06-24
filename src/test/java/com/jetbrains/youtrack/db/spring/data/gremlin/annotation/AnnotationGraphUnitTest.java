/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.annotation;

import com.jetbrains.youtrack.db.spring.data.gremlin.common.GremlinUtils;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.Network;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.Roadmap;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSource;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.source.GremlinSourceGraph;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationGraphUnitTest {

    @Test
    public void testAnnotationGraphDefaultCollection() {
        final GremlinSource source = GremlinUtils.toGremlinSource(Network.class);

        Assert.assertTrue(source instanceof GremlinSourceGraph);
        Assert.assertTrue(source.getLabel().isEmpty());
    }

    @Test
    public void testAnnotationGraphSpecifiedCollection() {
        final GremlinSource source = GremlinUtils.toGremlinSource(Roadmap.class);

        Assert.assertTrue(source instanceof GremlinSourceGraph);
        Assert.assertTrue(source.getLabel().isEmpty());
    }
}
