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
 * @author JKigwana
 */

/*global window, define, $, form2js, _, js2form, document, console */

define( "org/forgerock/openam/ui/policy/ManageEnvironmentsView", [
        "org/forgerock/commons/ui/common/main/AbstractView",
        "org/forgerock/openam/ui/policy/EditEnvironmentView",
        "org/forgerock/openam/ui/policy/OperatorRulesView",
        "org/forgerock/commons/ui/common/main/EventManager",
        "org/forgerock/commons/ui/common/util/Constants",
        "org/forgerock/commons/ui/common/main/Configuration",
        "org/forgerock/commons/ui/common/util/UIUtils"

], function(AbstractView, EditEnvironmentView, OperatorRulesView, eventManager, constants, conf, uiUtils ) {

    var ManageEnvironmentsView = AbstractView.extend({

        template: "templates/policy/ManageEnvironmentsTemplate.html",
        noBaseTemplate: true,
        element: "#environmentContainer",
        events: {
            'click  a#addEnvironment:not(.inactive)':   'addEnvironment',
            'click  a#addOperator:not(.inactive)':      'addOperator',
            'click  a#clear:not(.inactive)':            'onClear',

            "mousedown  #operator_0 li.environment:not(.editing)"  : 'setFocus',
            "mousedown  #operator_0 li.operator:not(.editing)"     : 'setFocus',

            'click  .operator > .item-button-panel > .icon-remove' : 'onDelete',
            'keyup  .operator > .item-button-panel > .icon-remove' : 'onDelete',
            'change  #operator_0 .operator select' : 'onSelect',

            'click    #operator_0 .environment > .item-button-panel > .icon-remove' :    'onDelete',
            'keyup    #operator_0 .environment > .item-button-panel > .icon-remove' :    'onDelete',
            'click    #operator_0 .environment > .item-button-panel > .icon-pencil' :    'toggleEditing',
            'keyup    #operator_0 .environment > .item-button-panel > .icon-pencil' :    'toggleEditing',
            'click    #operator_0 .environment > .item-button-panel > .icon-checkmark' : 'toggleEditing',
            'keyup    #operator_0 .environment > .item-button-panel > .icon-checkmark' : 'toggleEditing',
            'dblclick #operator_0 li.environment' :                                      'toggleEditing'
        },

        buttons:{},
        data: {},
        pickUpItem: null,
        environmentEntity: {},
        groupCounter: 0,

        render: function(args, callback, element) {

            var self = this;

            this.data.entity = args.entity;
            this.data.options = args.options;

            this.environmentEntity = null;

            if (this.data.entity.condition) {
                this.environmentEntity = this.data.entity.condition;
            }

            this.data.environments = [];
            this.data.operators = [];
            this.idCount = 1;
            this.sortingInitialised = false;

            _.each(this.data.options.availableEnvironments, function(item) {

                if(item.logical === true){
                    self.data.operators.push(item);
                }else{
                    self.data.environments.push(item);
                }

                delete item.config.type;
            });


            this.setElement(this.element);

            this.parentRender(function() {

                this.buttons.clearBtn       = this.$el.find("a#clear");
                this.buttons.addEnvironment = this.$el.find("a#addEnvironment");
                this.buttons.addOperator    = this.$el.find("a#addOperator");
                this.pickUpItem             = this.$el.find('#pickUpItem');

                if (self.data.operators.length === 0) {
                    this.buttons.addOperator.hide();
                }

                this.buildList();
                this.onClear();
                this.initSorting();

                if (callback) {callback();}
            });

        },

        buildList: function() {

            var self = this,
                newRule = null,
                operators = _.pluck( this.data.operators, 'title' ),
                buildListItem = null;

                buildListItem = function(data, container, parent) {

                    if( _.isArray(data) === false ){
                        data = [data];
                    }

                    _.each(data, function(item) {

                        if ( item && _.contains( operators, item.type )) {

                            newRule = new OperatorRulesView();
                            newRule.render(self.data, null, container, "environment_" + self.idCount );
                            newRule.setValue(item.type);
                            self.idCount++;

                        } else if ( !_.isEmpty(item) ) {

                            newRule = new EditEnvironmentView();
                            newRule.render({environments:self.data.environments}, null, container, self.idCount, item);
                            newRule.createListItem({environments:self.data.environments}, newRule.$el );
                            self.idCount++;
                        }

                        if ( item && item.conditions ) {
                            buildListItem( item.conditions, newRule.dropbox, item );
                        } else if (item && item.condition) {
                            buildListItem( item.condition, newRule.dropbox, item );
                        }

                    });
                };

            buildListItem(this.environmentEntity, this.$el.find('ol#dropbox'), null);
            this.delegateEvents();

        },

        initSorting: function() {

            var self = this,
                adjustment = {};

            // Adding the 'environment' property to the operator_0 means it can only accept one child.
            this.$el.find('#operator_0').data('itemData', {condition:{}});

            this.groupCounter++;

            this.$el.find("ol#dropbox").sortable({
                group: self.element + 'rule-creation-group' + self.groupCounter,
                exclude:'.item-button-panel, li.editing',
                delay: 100,

                // set item relative to cursor position
                onDragStart: function (item, container, _super) {

                    var offset = item.offset(),
                        pointer = container.rootGroup.pointer,
                        editEnvironmentView = null;
                    self.adjustment = {
                        left: pointer.left - offset.left + 5,
                        top: pointer.top - offset.top
                    };

                    self.setInactive(self.buttons.clearBtn, true);
                    self.setInactive(self.buttons.addEnvironment, false);
                    self.setInactive(self.buttons.addOperator, false);

                    item.focus();
                    item.css({width: item.width()}).addClass("dragged");
                    $("body").addClass("dragging");

                    if (!container.options.drop && item.hasClass('environment')) {
                        editEnvironmentView = $.extend( false, item, new EditEnvironmentView() );
                        editEnvironmentView.createListItem({environments:self.data.environments}, item);
                    }

                },

                onDrag: function (item, position) {
                    item.css({
                        left: position.left - self.adjustment.left,
                        top: position.top - self.adjustment.top
                    });
                },

                onDrop: function  (item, container, _super, event) {

                    var rule = null, clonedItem, newHeight, animeAttrs, data, jsonString;

                    if (container.options.drop) {

                        clonedItem = $('<li/>').css({height: 0, backgroundColor: 'transparent', borderColor: 'transparent'});
                        item.before(clonedItem);
                        newHeight = item.height();
                        animeAttrs = clonedItem.position();
                        animeAttrs.width = clonedItem.outerWidth()-10;
                        item.addClass('dropped');
                        clonedItem.animate({'height': newHeight }, 300, 'linear');
                        item.animate( animeAttrs, 300, function  () {

                            clonedItem.detach();
                            item.removeClass('dropped');

                            if (item.data().logical === true) {
                                rule = $.extend( false, item, new OperatorRulesView() );
                                rule.rebindElement();
                            }
                            item.focus();
                            _super(item, container);
                            self.save();
                        });

                    } else {

                        if (item.data().logical === undefined) {

                            rule = new EditEnvironmentView();
                            rule.render( {environments:self.data.environments}, null, self.pickUpItem, self.idCount, item.data().itemData );
                            self.idCount++;
                            item.remove();

                        } else {
                            item.focus();
                            _super(item, container);
                        }

                        self.save();
                    }

                    $("body").removeClass("dragging");

                    self.delegateEvents();

                },

                isValidTarget: function(item, container) {

                    if ( container.items.length > 0 &&
                         container.target.parent().data().itemData &&
                         container.target.parent().data().itemData.condition
                    ) {
                        return false;
                    } else {
                        return true;
                    }

                },

                serialize: function ($parent, $children, parentIsContainer) {

                   var result = $.extend({}, $parent.data().itemData);

                    if ( parentIsContainer ) {
                        return $children;
                    }

                    else if  ($children[0] ) {
                        if (result.conditions) {
                            result.conditions = $children;
                        } else if (result.condition) {
                            result.condition = $children[0];
                        }
                    }

                    delete result.subContainers;
                    delete result.sortable;
                    return result;
                }

            });

            self.pickUpItem.sortable({
                group: self.element + 'rule-creation-group' + self.groupCounter,
                drop: false
            });

            this.sortingInitialised = true;

        },

        editStart: function(item) {

           $('body').addClass('editing');
           var self = this,
               editEnvironmentView = new EditEnvironmentView();
               editEnvironmentView.render( {environments:self.data.environments}, null, self.pickUpItem, self.idCount, item.data().itemData );
           self.idCount++;

           editEnvironmentView.$el.addClass('editing');
           item.before(editEnvironmentView.$el) ;
           self.onClear();

           editEnvironmentView.$el.find('select#selection').focus();
        },

        editStop: function(item) {
            $('body').removeClass('editing');

            var editEnvironmentView = $.extend( false, item, new EditEnvironmentView() );
                editEnvironmentView.createListItem({environments:this.data.environments},  item);

            item.next().remove();
            this.save();
        },

        setInactive: function(button, state) {
            if (state) { button.addClass('inactive'); }
            else { button.removeClass('inactive'); }
        },

        onClear: function(e) {
            if(e) { e.preventDefault();}
            this.pickUpItem.children().remove();
            this.setInactive(this.buttons.clearBtn, true);
            this.setInactive(this.buttons.addEnvironment, false);
            this.setInactive(this.buttons.addOperator, false);
        },

        addOperator: function(e) {
            e.preventDefault();
            this.pickUpItem.children().remove();
            this.setInactive(this.buttons.clearBtn, false);
            this.setInactive(this.buttons.addEnvironment, false);
            this.setInactive(this.buttons.addOperator, true);

            var operatorRules = new OperatorRulesView();
                operatorRules.render(this.data, null, this.pickUpItem, "environment_" + this.idCount);
            this.idCount++;
        },

        addEnvironment: function(e) {
            e.preventDefault();
            this.pickUpItem.children().remove();
            this.setInactive(this.buttons.clearBtn, false);
            this.setInactive(this.buttons.addEnvironment, true);
            this.setInactive(this.buttons.addOperator, false);

            var editEnvironmentView = new EditEnvironmentView();
                editEnvironmentView.render({environments:this.data.environments}, null, this.pickUpItem, this.idCount);
            this.idCount++;
        },

        onSelect: function(e) {
            e.stopPropagation();
            this.save();
        },

        onDelete: function(e) {
            e.stopPropagation();
            if (e.type === 'keyup' && e.keyCode !== 13) { return;}
            var self = this, item = $(e.currentTarget).closest('li');
            item.animate({height: 0, paddingTop: 0, paddingBottom: 0,marginTop: 0,marginBottom: 0, opacity:0}, function() {
                item.remove();
                self.save();
            });
        },


        toggleEditing: function(e){
            if (e.type === 'keyup' && e.keyCode !== 13) { return;}
            var item = $(e.currentTarget).closest('li');
            if (item.hasClass('editing') ) {
                item.removeClass('editing');
                this.editStop(item);
            } else {
                this.editStart(item);
            }

        },

        setFocus: function(e) {
            e.stopPropagation();
            var target = $(e.target).is('select') || $(e.target).is('input') ?  e.target : e.currentTarget;
            $(target).focus();
        },

        save: function(e) {

            if (this.sortingInitialised !== true) {
                return;
            }
            var condition = this.$el.find('ol#dropbox').sortable('serialize').get();

            this.data.entity.condition = condition[0] || null;
            console.log("\nenvironment:",  JSON.stringify(this.data.entity.condition));
            console.log("\nenvironment:",  JSON.stringify(this.data.entity.condition, null, 2));


        }

    });

    return new ManageEnvironmentsView();
});