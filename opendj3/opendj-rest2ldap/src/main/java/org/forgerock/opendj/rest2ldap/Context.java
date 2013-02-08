/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2013 ForgeRock AS.
 */
package org.forgerock.opendj.rest2ldap;

import org.forgerock.json.resource.ServerContext;

/**
 * Common context information passed to containers and mappers.
 */
public final class Context {
    private final Config config;
    private final ServerContext context;

    Context(final Config config, final ServerContext context) {
        this.config = config;
        this.context = context;
    }

    /**
     * Returns the common configuration options.
     *
     * @return The common configuration options.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Returns the commons REST server context passed in with the REST request.
     *
     * @return The commons REST server context passed in with the REST request.
     */
    public ServerContext getServerContext() {
        return context;
    }
}
