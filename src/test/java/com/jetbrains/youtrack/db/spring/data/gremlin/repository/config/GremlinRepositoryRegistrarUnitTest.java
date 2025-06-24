/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.repository.config;

import com.jetbrains.youtrack.db.spring.data.gremlin.repository.config.EnableGremlinRepositories;
import com.jetbrains.youtrack.db.spring.data.gremlin.repository.config.GremlinRepositoryConfigurationExtension;
import com.jetbrains.youtrack.db.spring.data.gremlin.repository.config.GremlinRepositoryRegistrar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GremlinRepositoryRegistrarUnitTest {

    private GremlinRepositoryRegistrar registrar;

    @Before
    public void setup() {
        this.registrar = new GremlinRepositoryRegistrar();
    }

    @Test
    public void testGremlinRepositoryRegistrarGetters() {
        Assert.assertSame(this.registrar.getAnnotation(), EnableGremlinRepositories.class);
        Assert.assertTrue(this.registrar.getExtension() instanceof GremlinRepositoryConfigurationExtension);
    }
}
