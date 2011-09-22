/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 ForgeRock AS. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 */

package org.forgerock.openam.amsessionstore.impl;

import org.forgerock.i18n.LocalizableMessage;
import java.util.Set;
import java.util.logging.Level;
import org.forgerock.openam.amsessionstore.common.Log;
import org.forgerock.openam.amsessionstore.db.NotFoundException;
import org.forgerock.openam.amsessionstore.db.PersistentStoreFactory;
import org.forgerock.openam.amsessionstore.db.StoreException;
import org.forgerock.openam.amsessionstore.resources.ReadWithSecKeyResource;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import static org.forgerock.openam.amsessionstore.i18n.AmsessionstoreMessages.*;

/**
 * Implements the read with sec key resource functionality
 * 
 * @author steve
 */
public class ReadWithSecKeyResourceImpl extends ServerResource implements ReadWithSecKeyResource {
    @Get
    public Set<String> readWithSecKey(String id) {
        Set<String> records = null;
        
        try {
            records = PersistentStoreFactory.getPersistentStore().readWithSecKey(id);
        } catch (StoreException sex) {
            final LocalizableMessage message = DB_R_SEC_KEY.get(sex.getMessage());
            Log.logger.log(Level.WARNING, message.toString());
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, sex.getMessage());
        } catch (NotFoundException nfe) {
            final LocalizableMessage message = DB_R_SEC_KEY.get(nfe.getMessage());
            Log.logger.log(Level.WARNING, message.toString());
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, message.toString());
        } catch (Exception ex) {
            final LocalizableMessage message = DB_R_SEC_KEY.get(ex.getMessage());
            Log.logger.log(Level.WARNING, message.toString());
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, message.toString());
        }
        
        return records;
    }
}
