/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.mapping;

import com.jetbrains.youtrack.db.spring.data.gremlin.annotation.Vertex;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.TestConstants;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.Project;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.util.ClassTypeInformation;

public class GremlinMappingContextUnitTest {

    @Test
    public void testCreatePersistentProperty() {
        final GremlinMappingContext context = new GremlinMappingContext();
        final BasicGremlinPersistentEntity<Project> entity = context.createPersistentEntity(
                ClassTypeInformation.from(Project.class));

        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.findAnnotation(Vertex.class));
        Assert.assertEquals(entity.findAnnotation(Vertex.class).label(), TestConstants.VERTEX_PROJECT_LABEL);
    }
}
