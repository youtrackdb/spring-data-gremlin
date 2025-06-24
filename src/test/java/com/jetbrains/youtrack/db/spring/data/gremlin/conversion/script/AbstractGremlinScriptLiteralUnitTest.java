/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.conversion.script;

import com.jetbrains.youtrack.db.spring.data.gremlin.common.GremlinEntityType;
import com.jetbrains.youtrack.db.spring.data.gremlin.conversion.script.AbstractGremlinScriptLiteral;
import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinInvalidEntityIdFieldException;
import org.junit.Test;

public class AbstractGremlinScriptLiteralUnitTest extends AbstractGremlinScriptLiteral {

    @Test(expected = GremlinInvalidEntityIdFieldException.class)
    public void testEntityInvalidIdType() {
        final Double id = 12.342;
        AbstractGremlinScriptLiteral.generateEntityWithRequiredId(id, GremlinEntityType.EDGE);
    }

    @Test(expected = GremlinInvalidEntityIdFieldException.class)
    public void testPropertyInvalidIdType() {
        final Double id = 12.342;
        AbstractGremlinScriptLiteral.generatePropertyWithRequiredId(id);
    }
}
