/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common;

import com.jetbrains.youtrack.db.spring.data.gremlin.exception.GremlinIllegalConfigurationException;
import com.jetbrains.youtrack.db.spring.data.gremlin.telemetry.TelemetrySender;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.util.ser.Serializers;
import org.springframework.lang.NonNull;

import javax.annotation.PostConstruct;

public class GremlinFactory {

    private Cluster gremlinCluster;

    private GremlinConfig gremlinConfig;

    public GremlinFactory(@NonNull GremlinConfig gremlinConfig) {
        final int port = gremlinConfig.getPort();
        if (port <= 0 || port > 65535) {
            gremlinConfig.setPort(Constants.DEFAULT_ENDPOINT_PORT);
        }
        
        final int maxContentLength = gremlinConfig.getMaxContentLength();
        if (maxContentLength <= 0) {
            gremlinConfig.setMaxContentLength(Constants.DEFAULT_MAX_CONTENT_LENGTH);
        }

        this.gremlinConfig = gremlinConfig;
    }

    private Cluster createGremlinCluster() throws GremlinIllegalConfigurationException {
        final Cluster cluster;

        try {
            cluster = Cluster.build(this.gremlinConfig.getEndpoint())
                    .serializer(Serializers.valueOf(this.gremlinConfig.getSerializer()).simpleInstance())
                    .credentials(this.gremlinConfig.getUsername(), this.gremlinConfig.getPassword())
                    .enableSsl(this.gremlinConfig.isSslEnabled())
                    .maxContentLength(this.gremlinConfig.getMaxContentLength())
                    .port(this.gremlinConfig.getPort())
                    .create();
        } catch (IllegalArgumentException e) {
            throw new GremlinIllegalConfigurationException("Invalid configuration of Gremlin", e);
        }

        return cluster;
    }

    public Client getGremlinClient() {

        if (this.gremlinCluster == null) {
            this.gremlinCluster = this.createGremlinCluster();
        }

        return this.gremlinCluster.connect();
    }

    @PostConstruct
    private void sendTelemetry() {
        if (gremlinConfig.isTelemetryAllowed()) {
            final TelemetrySender sender = new TelemetrySender();

            sender.send(this.getClass().getSimpleName());
        }
    }
}
