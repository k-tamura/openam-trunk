/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 ForgeRock AS. All rights reserved.
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
 * @author JKigwana
 * @author Eugenia Sergueeva
 */

/*global window, define, $, _ */

define("org/forgerock/openam/ui/policy/conditions/ConditionAttrTimeView", [
    "org/forgerock/openam/ui/policy/conditions/ConditionAttrBaseView"
], function (ConditionAttrBaseView) {
    var ConditionAttrTimeView = ConditionAttrBaseView.extend({
        template: 'templates/policy/conditions/ConditionAttrTime.html',

        clickClockPicker: function (e) {
            e.stopPropagation();
            $(e.currentTarget).prev('input').clockpicker('show');
        },

        render: function (data, element, callback) {
            this.initBasic(data, element, 'float-left input-group');

            this.events['click .icon-clock'] = _.bind(this.clickClockPicker, this);

            this.parentRender(function () {
                this.initClockPickers();

                if (callback) {
                    callback();
                }
            });
        },

        initClockPickers: function () {
            this.$el.find('.clockpicker').each(function () {

                var clock = $(this);
                clock.clockpicker({
                    placement: 'top',
                    autoclose: true,
                    afterDone: function () {
                        clock.trigger('change');
                    }
                });
            });
        }
    });

    return ConditionAttrTimeView;
});
