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

define( "org/forgerock/openam/ui/policy/EditEnvironmentView", [
        "org/forgerock/commons/ui/common/main/AbstractView",
        "org/forgerock/commons/ui/common/util/UIUtils",
        "org/forgerock/commons/ui/common/main/EventManager",
        "org/forgerock/commons/ui/common/util/Constants",
        "org/forgerock/commons/ui/common/main/Configuration"
], function(AbstractView, uiUtils, eventManager, constants, conf) {

    var EditEnvironmentView = AbstractView.extend({

        events: {
            'change select#selection' :       'changeType',
            'change select:not(#selection)' : 'changeInput',
            'change input':                   'changeInput',
            'keyup  input':                   'changeInput',
            'autocompletechange input':       'changeInput',
            'click .buttonControl a.button':  'buttonControlClick',
            'keyup .buttonControl a.button':  'buttonControlClick',
            'click .clockpicker':             'clickClockPicker',
            'click .icon-clock':              'clickClockPicker'
        },

        data: {},
        weekdays:[
            {value:'mon', title:'Monday'},
            {value:'tue', title:'Tuesday'},
            {value:'wed', title:'Wednesday'},
            {value:'thu', title:'Thursday'},
            {value:'fri', title:'Friday'},
            {value:'sat', title:'Saturday'},
            {value:'sun', title:'Sunday'}
        ],

        mode:'append',

        render: function( schema, callback, element, itemID, itemData ) {
            var self = this;
            this.setElement(element);

            this.data = $.extend(true, [], schema);
            this.data.itemID = itemID;

            this.$el.append(uiUtils.fillTemplateWithData("templates/policy/EditEnvironmentTemplate.html", this.data));

            this.setElement('#environment_' + itemID );
            this.delegateEvents();

            if (itemData) {
                // Temporay fix, the name attribute is being added by the server after the policy is created.
                // TODO: Serverside solution required
                delete itemData.name;
                this.$el.data('itemData',itemData);
                this.$el.find('select#selection').val(itemData.type).trigger('change');
            }

            this.$el.find('select#selection').focus();

            if (callback) {callback();}
        },

        createListItem: function(allEnvironments, item){

            item.focus(); //  Required to trigger changeInput.
            this.data.conditions = allEnvironments;
            var html = '';
            if (item.data().itemData) {
                _.map(item.data().itemData, function(value, key) {
                    html += '<div><h3>'+key+'</h3><span>'+value+'</span></div>\n';
                });
            }
            if (html === '') {
                html = '<div class="invalid"><h3>blank</h3><span>edit rule...</span></div>';
            }
            item.find('.item-data').html(html);
            this.setElement('#'+item.attr('id') );
            this.delegateEvents();
        },

        changeInput: function(e) {

            e.stopPropagation();
            var label = $(e.currentTarget).parent().children('label').text(),
                inputGroup = $(e.currentTarget).closest('div.input-group'),
                ifPopulated = false;

            this.$el.data().itemData[label] = e.currentTarget.value;

            inputGroup.find('input, select').each(function(){
               if(this.value !== ''){
                  ifPopulated = true;
               }
            });

            inputGroup.find('input, select').each(function(){
                $(this).prop('required', ifPopulated);
            });
        },

        buttonControlClick: function(e){
            if (e.type === 'keyup' && e.keyCode !== 13) { return;}
            var target = $(e.currentTarget),
                buttonControl = target.closest('ul.buttonControl'),
                label = buttonControl.prev('label').text();
            this.$el.data().itemData[ label ] = e.currentTarget.innerText === "true";
            buttonControl.find('li a').removeClass('selected');
            target.addClass('selected');
        },


        initDatePickers: function() {
          this.$el.find( "#startDate" ).datepicker({
            numberOfMonths: 2,
            onClose: function( selectedDate ) {
              $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
            }
          });
          this.$el.find("#endDate" ).datepicker({
            numberOfMonths: 2,
            onClose: function( selectedDate ) {
              $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
            }
          });
        },

        initClockPickers: function() {
            this.$el.find('.clockpicker').each(function(){

              var clock = $(this);
              clock.clockpicker({
                  placement: 'top',
                  autoclose: true,
                  //default: 'now',
                  afterDone: function() {
                      clock.trigger('change');
                  }
              });

            });
        },

        clickClockPicker: function(e) {
            e.stopPropagation();
            var target = $(e.currentTarget).is('input') ? $(e.currentTarget) : $(e.currentTarget).prev('input');
            target.clockpicker('show');
        },

        getTimeZones: function(){

            var self = this,
                setTimeZones = function(){
                    self.$el.find('#enforcementTimeZone').autocomplete({
                        source: self.data.timezones
                    });
                };

            if (self.data.timezones) {
                setTimeZones();
                return;
            }

            $.ajax({
                url: 'timezones.json',
                dataType: "json",
                cache: true
            }).then( function(data){
                self.data.timezones = data.timezones;
                setTimeZones();
            });

        },

        changeType: function(e) {
            e.stopPropagation();
            var self         = this,
                itemData     = {},
                schema       = {},
                html         = '',
                returnVal    = '',
                selectedType = e.target.value,
                delay        = self.$el.find('.field-float-pattern').length > 0 ? 500 : 0,
                buildHTML    = function(schemaProps) {

                    var count = 0,
                        pattern   = null;

                    returnVal = '';

                    if (itemData.type === "Time") {

                        returnVal += uiUtils.fillTemplateWithData("templates/policy/ConditionAttrTimeDate.html", {
                            weekdays:self.weekdays,
                            data:itemData,
                            id:count
                        });

                    } else {

                        _.map(schemaProps, function(value, key) {

                            returnVal += '\n';

                            if (value.type === 'string' || value.type === 'number') {

                                if (value["enum"]) {

                                    returnVal +=  uiUtils.fillTemplateWithData("templates/policy/ConditionAttrEnum.html", {data:value, title:key, selected:itemData[key], id:count});

                                } else {
                                    if (key === 'startIp' || key === 'endIp') {
                                       pattern="^(((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]?\\d)))((\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]?\\d))){3}|(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]?\\d))){5})";
                                    } else if( value.type === 'number' ){
                                       pattern="[-+]?[0-9]*[.,]?[0-9]+";
                                    } else {
                                       pattern = null;
                                    }
                                    returnVal +=  uiUtils.fillTemplateWithData("templates/policy/ConditionAttrString.html", {data:itemData[key], title:key, id:count, pattern:pattern});
                                }

                            } else if (value.type === 'boolean' ) {
                                // Ignoring the required property and assumming it defaults to false. See AME-4324
                                returnVal +=  uiUtils.fillTemplateWithData("templates/policy/ConditionAttrBoolean.html", {data:value, title:key, selected:itemData[key]});

                            } else {
                                console.error('Unexpected data type:',key,value);
                            }

                            count++;
                        });
                    }

                    return returnVal;
                };

            schema =  _.findWhere(this.data.conditions, {title: selectedType}) || {};

            if (this.$el.data().itemData && this.$el.data().itemData.type === selectedType) {
                itemData = this.$el.data().itemData;
            } else {
                itemData.type = schema.title;
                _.map(schema.config.properties, function(value,key) {
                    switch (value.type) {
                        case 'string':
                            itemData[key] = '';
                        break;
                        case 'number':
                            itemData[key] = 0;
                        break;
                        case 'boolean':
                            itemData[key] = false;
                        break;
                        default:
                            console.error('Unexpected data type:',key,value);
                        break;
                    }
                });
                self.$el.data('itemData',itemData);
            }

            if (itemData) {

                html = buildHTML(schema.config.properties);

                this.$el.find('.field-float-pattern')
                    .find('label').removeClass('showLabel')
                    .next('input')
                    .addClass('placeholderText')
                    .prop('readonly', true);

                this.$el.find('.field-float-select select:not(#selection)')
                    .addClass('placeholderText')
                    .prev('label')
                    .removeClass('showLabel');


                this.$el.find('.ruleHelperText').fadeOut(500);

                // setTimeout needed to delay transitions.
                setTimeout( function() {

                    self.$el.find('#conditionAttrTimeDate').remove();
                    self.$el.find('.field-float-pattern').remove();
                    self.$el.find('.field-float-select:not(#typeSelector)').remove();

                    self.$el.find('#typeSelector').after( html );

                    if (itemData.type === "Time") {
                        self.initClockPickers();
                        self.initDatePickers();
                        self.getTimeZones();
                    }

                    setTimeout( function() {

                        self.$el.find('.field-float-pattern')
                            .find('label').addClass('showLabel')
                            .next('input, div input')
                            .addClass('placeholderText')
                            .prop('readonly', false);

                        self.$el.find('.field-float-select select:not(#selection)')
                            .removeClass('placeholderText')
                            .prop('readonly', false)
                            .prev('label')
                            .addClass('showLabel');

                        self.delegateEvents();


                    }, 10);
                }, delay);

            }
        }

    });

    return EditEnvironmentView; 

});
