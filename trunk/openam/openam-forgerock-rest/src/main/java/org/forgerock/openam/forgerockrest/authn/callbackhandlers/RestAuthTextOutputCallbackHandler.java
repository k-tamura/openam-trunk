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
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2013-2014 ForgeRock AS.
 */

package org.forgerock.openam.forgerockrest.authn.callbackhandlers;

import org.forgerock.json.fluent.JsonValue;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthResponseException;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthException;
import org.forgerock.openam.utils.JsonValueBuilder;

import javax.security.auth.callback.TextOutputCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Defines methods to convert a TextOutputCallback to a JSON representation.
 */
public class RestAuthTextOutputCallbackHandler extends AbstractRestAuthCallbackHandler<TextOutputCallback>  {

    private static final String CALLBACK_NAME = "TextOutputCallback";

    /**
     * TextOutputCallback is used to send information to the client so there is nothing to update the callback with
     * so always returns false.
     *
     * {@inheritDoc}
     */
    boolean doUpdateCallbackFromRequest(HttpServletRequest request, HttpServletResponse response,
            TextOutputCallback callback) throws RestAuthResponseException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public TextOutputCallback handle(HttpServletRequest request, HttpServletResponse response,
            JsonValue postBody, TextOutputCallback originalCallback) {
        return originalCallback;
    }

    /**
     * {@inheritDoc}
     */
    public String getCallbackClassName() {
        return CALLBACK_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public JsonValue convertToJson(TextOutputCallback callback, int index) {

        //todo replace JSONObject with JsonValue's toString(), when toString() escapes correctly
        String message = JSONObject.quote(callback.getMessage());
        int messageType = callback.getMessageType();

        if (message.length() >= 2) { //JSONObject.quote cannot return null
            message = message.substring(1, message.length() - 1);
        }

        JsonValue jsonValue = JsonValueBuilder.jsonValue()
                .put("type", CALLBACK_NAME)
                .array("output")
                .add(createOutputField("message", message))
                .addLast(createOutputField("messageType", String.valueOf(messageType)))
                .build();

        return jsonValue;
    }

    /**
     * {@inheritDoc}
     */
    public TextOutputCallback convertFromJson(TextOutputCallback callback, JsonValue jsonCallback) throws RestAuthException {

        validateCallbackType(CALLBACK_NAME, jsonCallback);

        // Nothing to do here as TextOutputCallback is purely used to send information to the client.

        return callback;
    }
}
