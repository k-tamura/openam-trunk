define("org/forgerock/openam/extensions/authmodules/adaptivedeviceprint/infocollectors/TimezoneCollector",["org/forgerock/openam/extensions/authmodules/adaptivedeviceprint/AbstractDevicePrintInfoCollector"],function(a){var b=new a;return b.gatherInformation=function(){var a={},b=(new Date).getTimezoneOffset();return b?(a.timezone=b,a):(console.error("TimezoneCollector: timezone not defined"),null)},b})