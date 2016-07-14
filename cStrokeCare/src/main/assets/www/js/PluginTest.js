cordova.define("cordova/plugin/PluginTest", function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var Bluetooth = function() {};

	// ���ե�
	Bluetooth.prototype.getMessage = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'getMessage', []);
	}

	// ��l���Ť�
	Bluetooth.prototype.init = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'init', []);
	}

	// �إ�socket
	Bluetooth.prototype.buildSocket = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'buildSocket', []);
	}

	// ���o�[�t��
	Bluetooth.prototype.getAcceleration = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'getAcceleration', []);
	}

	module.exports = new Bluetooth();
});