/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common;

import com.jetbrains.youtrack.db.spring.data.gremlin.config.AbstractGremlinConfiguration;
import com.jetbrains.youtrack.db.spring.data.gremlin.repository.config.EnableGremlinRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableGremlinRepositories
@PropertySource(value = {"classpath:application.properties"})
@EnableConfigurationProperties(TestGremlinProperties.class)
public class TestRepositoryConfiguration extends AbstractGremlinConfiguration {

    @Autowired
    private TestGremlinProperties testProps;

    @Override
    public GremlinConfig getGremlinConfig() {
        return GremlinConfig.builder(testProps.getEndpoint(), testProps.getUsername(), testProps.getPassword())
                .port(testProps.getPort())
                .telemetryAllowed(testProps.isTelemetryAllowed())
                .sslEnabled(testProps.isSslEnabled())
                .serializer(testProps.getSerializer())
                .build();
    }
}
