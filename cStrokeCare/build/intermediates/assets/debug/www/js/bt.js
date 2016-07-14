cordova.define("cordova/plugin/bt", function(require, exports, module){
	var exec = require('cordova/exec');
	var Bluetooth = function() {};
	Bluetooth.prototype.bluetooth_test = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'bt', 'get_btstatus', []);
	}
	module.exports = new Bluetooth();
});
