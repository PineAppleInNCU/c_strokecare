cordova.define("cordova/plugin/AndroidWear", function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var AndroidWear = function() {};

	// ∞ı¶ÊAndroidWear Plugin
	AndroidWear.prototype.start = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'AndroidWear', 'start', []);
	}

	module.exports = new AndroidWear();
});