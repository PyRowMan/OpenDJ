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
 * Copyright 2012-2013 ForgeRock AS.
 */
package org.forgerock.opendj.rest2ldap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.forgerock.json.fluent.JsonPointer;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.ResultHandler;
import org.forgerock.opendj.ldap.Attribute;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.Filter;

/**
 * An attribute mapper is responsible for converting JSON values to and from
 * LDAP attributes.
 */
public abstract class AttributeMapper {
    /*
     * This interface is an abstract class so that methods can be made package
     * private until API is finalized.
     */

    AttributeMapper() {
        // Nothing to do.
    }

    /**
     * Adds the names of the LDAP attributes required by this attribute mapper
     * which are associated with the provided resource attribute to the provided
     * set.
     * <p>
     * Implementations should only add the names of attributes found in the LDAP
     * entry directly associated with the resource.
     *
     * @param c
     *            The context.
     * @param jsonAttribute
     *            The name of the resource attribute requested by the client.
     * @param ldapAttributes
     *            The set into which the required LDAP attribute names should be
     *            put.
     */
    abstract void getLDAPAttributes(Context c, JsonPointer jsonAttribute, Set<String> ldapAttributes);

    /**
     * Transforms the provided REST comparison filter parameters to an LDAP
     * filter representation, invoking a completion handler once the
     * transformation has completed.
     * <p>
     * If this attribute mapper is not responsible for mapping the provided JSON
     * attribute then the result handler's {@link ResultHandler#handleResult
     * handleResult} method must be invoked with the value {@code null}. If this
     * attribute mapper is responsible for mapping the JSON attribute, but an
     * error occurred while constructing the LDAP filter, then the result
     * handler's {@link ResultHandler#handleError handleError} method must be
     * invoked with an appropriate exception indicating the problem which
     * occurred.
     *
     * @param c
     *            The context.
     * @param type
     *            The type of REST comparison filter.
     * @param jsonAttribute
     *            The name of the resource attribute to be filtered.
     * @param operator
     *            The name of the extended operator to use for the comparison,
     *            or {@code null} if {@code type} is not
     *            {@link FilterType#EXTENDED}.
     * @param valueAssertion
     *            The value assertion, or {@code null} if {@code type} is
     *            {@link FilterType#PRESENT}.
     * @param h
     *            The result handler.
     */
    abstract void getLDAPFilter(Context c, FilterType type, JsonPointer jsonAttribute,
            String operator, Object valueAssertion, ResultHandler<Filter> h);

    /**
     * Transforms attributes contained in the provided LDAP entry to JSON
     * content, invoking a completion handler once the transformation has
     * completed.
     * <p>
     * This method is invoked whenever an LDAP entry is converted to a REST
     * resource, i.e. when responding to read, query, create, put, or patch
     * requests.
     *
     * @param c
     *            The context.
     * @param e
     *            The LDAP entry to be converted to JSON.
     * @param h
     *            The result handler.
     */
    abstract void toJSON(Context c, Entry e, ResultHandler<Map<String, Object>> h);

    /**
     * Transforms JSON content in the provided JSON value to LDAP attributes,
     * invoking a completion handler once the transformation has completed.
     * <p>
     * This method is invoked whenever a REST resource is converted to an LDAP
     * entry or LDAP modification, i.e. when performing create, put, or patch
     * requests.
     *
     * @param c
     *            The context.
     * @param v
     *            The JSON value to be converted to LDAP attributes.
     * @param h
     *            The result handler.
     */
    abstract void toLDAP(Context c, JsonValue v, ResultHandler<List<Attribute>> h);

    // TODO: methods for obtaining schema information (e.g. name, description,
    // type information).
    // TODO: methods for creating sort controls.
}
