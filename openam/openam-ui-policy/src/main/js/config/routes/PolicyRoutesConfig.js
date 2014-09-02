/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014 ForgeRock AS. All Rights Reserved
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
 */

/*global define*/

define("config/routes/PolicyRoutesConfig", function () {
    return {
        "manageApps": {
            view: "org/forgerock/openam/ui/policy/ManageApplicationsView",
            url: "apps/",
            role: "ui-admin"
        },
        "editApp": {
            view: "org/forgerock/openam/ui/policy/EditApplicationView",
            url: /^app\/([%A-Za-z0-9]*)(?:\/){0,1}$/,
            role: "ui-admin",
            pattern: "app/?"
        },
        "managePolicies": {
            view: "org/forgerock/openam/ui/policy/ManagePoliciesView",
            url: /^app\/([%A-Za-z0-9]+)\/policies\//,
            role: "ui-admin",
            pattern: "app/?/policies/"
        },
        "editPolicy": {
            view: "org/forgerock/openam/ui/policy/EditPolicyView",
            url: /^app\/([%A-Za-z0-9]+)\/policy\/([%A-Za-z0-9]*)(?:\/){0,1}$/,
            role: "ui-admin",
            pattern: "app/?/policy/?"
        }
    };
});