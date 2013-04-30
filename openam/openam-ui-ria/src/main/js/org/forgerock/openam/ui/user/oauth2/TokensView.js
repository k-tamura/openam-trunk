/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2013 ForgeRock AS. All rights reserved.
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

/*global define, $, form2js, _ , console*/

define("org/forgerock/openam/ui/user/oauth2/TokensView", [
    "org/forgerock/commons/ui/common/main/AbstractView",
    "org/forgerock/openam/ui/user/delegates/TokenDelegate",
    "org/forgerock/commons/ui/common/main/EventManager",
    "org/forgerock/commons/ui/common/util/Constants",
    "dataTable",
    "org/forgerock/commons/ui/common/main/i18nManager"
], function(AbstractView, tokensDelegate, eventManager, constants, dataTable, i18nManager) {


    var TokensView = AbstractView.extend({
        template: "templates/openam/oauth2/TokensTemplate.html",

        delegate: tokensDelegate,

        events: {
            "click checkbox": "select",
            "click input[type=submit]": "formSubmit"
        },

        select: function(event) {
            console.log("oauth2 token selected");
            event.stopPropagation();
        },

        render: function(args, callback) {
            this.parentRender(function() {
                this.reloadData();

                if(callback) {
                    callback();
                }
            });
        },

        reloadData: function(){

            $('#tokensTable').dataTable( {
                "bProcessing": true,
                "sAjaxSource": "",
                "fnServerData": function(sUrl, aoData, fnCallback, oSettings) {
                    tokensDelegate.getAllTokens(function(tokens) {
                        var data = {aaData: tokens}, i, cleanScope, cleanDate;

                        for(i = 0; i < data.aaData.length; i++) {
                            data.aaData[i].selector = '<input name="selector" id="' + data.aaData[i].id +  '" type="checkbox" />';

                            if (typeof data.aaData[i].scope !== 'undefined'){
                                cleanScope = data.aaData[i].scope;
                            } else {
                                cleanScope = '-';
                            }
                            data.aaData[i].cleanScope = $('<span class="cleanScope" />').text(cleanScope).wrap('<p>').parent().html();
                            cleanDate = new Date(Number(data.aaData[i].expireTime));
                            data.aaData[i].cleanDate = $('<span class="cleanDate" />').text(cleanDate).wrap('<p>').parent().html();
                        }

                        fnCallback(data);
                    });
                },
                "aoColumns": [
                    {
                        "mData": "selector",
                        "bSortable": false
                    },
                    {
                        "mData": "clientID",
                        "sTitle": $.t("templates.oauth.clientID")
                    },
                    {
                        "mData": "id",
                        "sTitle": $.t("templates.oauth.tokenID")
                    },
                    {
                        "mData": "cleanScope",
                        "sTitle": $.t("templates.oauth.scope")
                    },
                    {
                        "mData": "cleanDate",
                        "sTitle": $.t("templates.oauth.expireDate")
                    },
                    {
                        "mData": "tokenName",
                        "sTitle": $.t("templates.oauth.tokenType")
                    }
                ],
                "oLanguage": {
                    "sUrl": "locales/" + i18nManager.language + "/translation.json"
                },
                "sDom": 'l<"deleteSelected">f<"clear">rt<"clear">ip<"clear">',
                "sPaginationType": "full_numbers",
                "fnInitComplete": function(oSettings, json) {
                    $(".deleteSelected").html('<input type="submit" class="button orange floatRight" value="' + $.t("common.form.deleteSelected") + '" >');
                },
                "fnRowCallback": function(row, data, displayindex) {
                    $(row).children().not(':first').click(function(){
                        var id = data.id[0], htmlCode, table, temp = row, td;
                        tokensDelegate.getTokenByID(function (tokenInfo){
                            var output;

                            output = '<table width="100%" cellpadding="5" cellspacing="0" border="0" style="padding:25px;">';
                            if (tokenInfo.realm){
                                output += '<tr><td>' + $.t("templates.oauth.realm") + '</td><td>'+tokenInfo.realm+'</td></tr>';
                            }
                            if (tokenInfo.refreshToken){
                                output += '<tr><td>' + $.t("templates.oauth.refreshToken") + '</td><td>'+tokenInfo.refreshToken+'</td></tr>';
                            }
                            if (tokenInfo.redirectURI){
                                output += '<tr><td>' + $.t("templates.oauth.redirectURI") + '</td><td>'+tokenInfo.redirectURI+'</td></tr>';
                            }
                            if (tokenInfo.userName){
                                output += '<tr><td>' + $.t("templates.oauth.username") + '</td><td>'+tokenInfo.userName+'</td></tr>';
                            }
                            if (tokenInfo.parent){
                                output += '<tr><td>' + $.t("templates.oauth.parent") + '</td><td>'+tokenInfo.parent+'</td></tr>';
                            }
                            output += '</table>';


                            td = $(temp).next().children(0);
                            if (td.attr('class') === 'details'){
                                td.html(output);
                            }
                        }, null, id);

                        table = $('#tokensTable').dataTable();

                        if ( table.fnIsOpen(row) ) {
                            table.fnClose( row );
                        } else {
                            table.fnOpen( row, htmlCode, 'details' );
                        }
                    });
                    return row;
                }
            });
        },

        formSubmit: function(event) {
            var data = {};

            event.preventDefault();
            event.stopPropagation();

            $("input:checked", $('#tokensTable').dataTable().fnGetNodes()).each(function(){
                var id, td;
                td = $(this);
                id = td.attr('id');
                tokensDelegate.deleteToken(
                    function(id) {
                        console.info("Token Deleted id:", id);
                        location.reload(true);
                    },
                    function(id){
                        console.error("Unable to delete token id:", id);
                    },
                    id);
            });

        }

    });

    return new TokensView();
});
