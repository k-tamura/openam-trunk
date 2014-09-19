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

define("org/forgerock/openam/ui/common/components/Accordion", function () {
    var Accordion,
        activeStepClass = 'active-step',
        disabledStepClass = 'accordion-step-disabled',
        headerClass = '.accordion-header',
        stepClass = '.accordion-step',

        collapse = function ($el) {
            $el.removeClass(activeStepClass).slideUp();
        },

        expand = function ($el) {
            $el.addClass(activeStepClass).slideDown();
        };

    Accordion = function ($el, options) {
        options = options ? options : {};

        var self = this;

        this.$headers = $el.find(headerClass);
        this.$headers.on('click', function (e) {
            self.expandCollapse(e);
        });

        this.$sections = $el.find(stepClass).hide();
        this.$sections.each(function (id, el) {
            $(el).data('stepId', id);
        });

        if (options.disabled) {
            this.disableSections();
        }

        this.setActive(options.active ? options.active : 0);
    };

    /**
     * Sections headers handler.
     */
    Accordion.prototype.expandCollapse = function (e) {
        e.preventDefault();

        var $this = $(e.target),
            disabled = $this.hasClass(disabledStepClass),
            $targetStep = $this.next(),
            id = $targetStep.data('stepId');

        if (!disabled && id !== this.getActive()) {
            this.setActive(id);
            this.$activeSection = $targetStep;
        }
    };

    /**
     * Returns index of currently active step.
     */
    Accordion.prototype.getActive = function () {
        return this.activeId;
    };

    /**
     * Attaches event handler to the accordion.
     */
    Accordion.prototype.on = function (eventName, handler) {
        $(this).on(eventName, handler);
    };

    /**
     * Enables selection of a section of the supplied index.
     *
     * @param id section index
     */
    Accordion.prototype.enableStep = function (id) {
        $(this.$headers[id]).removeClass(disabledStepClass);
    };

    /**
     * Disables selection of steps.
     */
    Accordion.prototype.disableSections = function () {
        this.$headers.addClass('accordion-step-disabled');
        this.$headers.removeClass('step-active');
    };

    /**
     * Collapses currently active step and expands requested step.
     */
    Accordion.prototype.setActive = function (id) {
        $(this).trigger('beforeChange', [id]);

        if (this.$activeSection) {
            collapse(this.$activeSection);
            this.enableStep(this.activeId);
        }

        this.activeId = id;
        this.enableStep(this.activeId);

        this.$activeSection = $(this.$sections[this.activeId]);
        expand(this.$activeSection);

        this.$headers.removeClass('step-active');
        $(this.$headers[id]).addClass('step-active').focus();
    };

    return Accordion;
});