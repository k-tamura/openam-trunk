/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014 ForgeRock AS. All rights reserved.
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

/**
 * @author Eugenia Sergueeva
 */

/*global window, define, $, form2js, _, js2form, document, console */

define("org/forgerock/openam/ui/policy/ManageApplicationsView", [
    "org/forgerock/commons/ui/common/main/AbstractView",
    "org/forgerock/commons/ui/common/util/UIUtils"
], function (AbstractView, uiUtils) {
    var ManageApplicationsView = AbstractView.extend({
        baseTemplate: "templates/policy/BaseTemplate.html",
        template: "templates/policy/ManageApplicationsTemplate.html",

        render: function (args, callback) {
            var appLinkFormatter = function (cellvalue, options, rowObject) {
                    return '<a href="#app/' + cellvalue + '">' + cellvalue + '</a>';
                },
                policyLinkFormatter = function (cellvalue, options, rowObject) {
                    return '<a href="#app/' + cellvalue + '/policies/">View</a>';
                };

            this.parentRender(function () {
                var options = {
                    view: this,
                    id: '#manageApps',
                    url: '/openam/json/applications?_queryFilter=true',
                    colNames: ['Name', 'Realm', 'Type', 'Last Modified', 'Policies'],
                    colModel: [
                        {name: 'name', formatter: appLinkFormatter, width: 260},
                        {name: 'realm', width: 70},
                        {name: 'applicationType', width: 260},
                        {name: 'lastModifiedDate', width: 260},
                        {name: 'name', formatter: policyLinkFormatter,  width: 70}
                    ],
                    width: '920',
                    pager: '#appsPager',
                    callback: callback
                };

                uiUtils.buildRestResponseBasedJQGrid(options);
            });
        }
    });

    return new ManageApplicationsView();
});
