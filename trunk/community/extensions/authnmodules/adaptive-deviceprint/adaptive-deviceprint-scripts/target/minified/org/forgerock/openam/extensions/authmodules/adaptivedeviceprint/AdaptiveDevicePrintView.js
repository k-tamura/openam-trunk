define(["org/forgerock/libwrappers/JQueryWrapper"],function(a){var b={};return b.getLoginForm=function(){return a("[name=Login]")},b.getDevicePrintInfoInput=function(){return b.getLoginForm().find("[name=devicePrintInfo]").length||b.getLoginForm().append("<input name='devicePrintInfo' type='hidden' />"),b.getLoginForm().find("[name=devicePrintInfo]")},b})