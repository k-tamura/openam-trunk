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

/*global window, define, $, _, document, console, sessionStorage, FileReader */

define("org/forgerock/openam/ui/policy/ManageApplicationsView", [
    "org/forgerock/openam/ui/policy/GenericGridView",
    "org/forgerock/commons/ui/common/util/UIUtils",
    "org/forgerock/commons/ui/common/main/Router",
    "org/forgerock/commons/ui/common/util/Constants",
    "org/forgerock/commons/ui/common/main/Configuration",
    "org/forgerock/commons/ui/common/main/EventManager",
    "org/forgerock/openam/ui/policy/PolicyDelegate"
], function (GenericGridView, uiUtils, router, constants, conf, eventManager, policyDelegate) {
    var ManageApplicationsView = GenericGridView.extend({
        template: "templates/policy/ManageApplicationsTemplate.html",

        events: {
            'click .icon-pencil': 'editApplication',
            'click .icon-file': 'viewPolicies',
            'click #deleteItems': 'deleteApplications',
            'click #importPolicies': 'startImportPolicies',
            'click #exportPolicies': 'exportPolicies',
            'change #realImport': 'readImportFile'
        },

        render: function (args, callback) {
            var self = this,
                actionsFormatter = function (cellvalue, options, rowObject) {
                    return uiUtils.fillTemplateWithData("templates/policy/ApplicationTableCellActionsTemplate.html");
                };

            this.initBaseView('templates/policy/ApplicationTableGlobalActionsTemplate.html', 'PE-mng-apps-sel');

            this.data.realm = conf.globalData.auth.realm;

            this.parentRender(function () {
                var options,
                    additionalOptions;

                if (this.data.realm !== "/") {
                    this.subrealm = this.data.realm;
                } else {
                    this.subrealm = "";
                }

                this.setGridButtonSet();

                options = {
                    url: '/openam/json' + this.subrealm + '/applications',
                    colNames: ['', '', 'Name', 'Description', 'Application Base', 'Author', 'Created', 'Last Modified'],
                    colModel: [
                        {name: 'iconChB', width: 40, sortable: false, formatter: self.checkBoxFormatter, frozen: true,
                            title: false, search: false},
                        {name: 'actions', width: 65, sortable: false, formatter: actionsFormatter, frozen: true,
                            title: false, search: false},
                        {name: 'name', width: 230, frozen: true},
                        {name: 'description', width: 220, sortable: false},
                        {name: 'resources', width: 340, sortable: false, search: false,
                            formatter: uiUtils.commonJQGridFormatters.arrayFormatter},
                        {name: 'createdBy', width: 250, hidden: true},
                        {name: 'creationDate', width: 150, formatter: uiUtils.commonJQGridFormatters.dateFormatter,
                            hidden: true, search: false},
                        {name: 'lastModifiedDate', width: 150, formatter: uiUtils.commonJQGridFormatters.dateFormatter,
                            hidden: true, search: false}
                    ],
                    beforeSelectRow: function (rowId, e) {
                        var checkBoxCellSelected = self.isCheckBoxCellSelected(e);
                        if (!checkBoxCellSelected && !$(e.target).hasClass('icon-pencil')) {
                            self.viewPolicies(e);
                        }

                        return checkBoxCellSelected;
                    },
                    onSelectRow: function (rowid, status, e) {
                        self.onRowSelect(rowid, status, e);
                    },
                    sortname: 'name',
                    width: 920,
                    shrinkToFit: false,
                    pager: '#appsPager'
                };

                additionalOptions = {
                    search: true,
                    columnChooserOptions: {
                        width: 501,
                        height: 180
                    },
                    storageKey: constants.OPENAM_STORAGE_KEY_PREFIX + 'PE-mng-apps-sel-col'
                };

                this.grid = uiUtils.buildRestResponseBasedJQGrid(this, '#manageApps', options, additionalOptions, callback);

                this.grid.on('jqGridAfterInsertRow', function (e, rowid, rowdata) {
                    self.selectRow(e, rowid, rowdata);
                });

                this.grid.jqGrid('setFrozenColumns');

                this.reloadGlobalActionsTemplate();
            });
        },

        editApplication: function (e) {
            router.routeTo(router.configuration.routes.editApp, {args: [this.getAppName(e)], trigger: true});
        },

        viewPolicies: function (e) {
            router.routeTo(router.configuration.routes.managePolicies, {args: [this.getAppName(e)], trigger: true});
        },

        getAppName: function (e) {
            return this.grid.getRowData(this.getSelectedRowId(e)).name;
        },

        deleteApplications: function (e) {
            e.preventDefault();

            if ($(e.target).hasClass('inactive')) {
                return;
            }

            var self = this, i, promises = [];

            for (i = 0; i < self.selectedItems.length; i++) {
                promises.push(policyDelegate.deleteApplication(self.selectedItems[i]));
            }
            this.deleteItems(e, promises);
        },

        startImportPolicies: function () {
            // Triggering the click on the hidden input with type "file" to upload the file
            this.$el.find("#realImport").trigger("click");
        },

        importPolicies: function (e) {
            policyDelegate.importPolicies( e.target.result)
                .done( function () {
                    eventManager.sendEvent(constants.EVENT_DISPLAY_MESSAGE_REQUEST, "policiesUploaded");
                })
                .fail( function () {
                    eventManager.sendEvent(constants.EVENT_DISPLAY_MESSAGE_REQUEST, "policiesUploadFailed");
                });
        },

        readImportFile: function () {
            var file = this.$el.find("#realImport")[0].files[0],
                reader = new FileReader();
            reader.onload = this.importPolicies;
            if (file) {
                reader.readAsText(file, "UTF-8");
            }

        },

        exportPolicies: function () {
            this.$el.find("#exportPolicies").attr('href', constants.host + "/openam" + this.subrealm + "/xacml/policies");
        }
    });

    return new ManageApplicationsView();
})
;