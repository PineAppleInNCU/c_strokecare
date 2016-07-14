cordova.define("cordova/plugin/PluginTest", function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var Bluetooth = function() {};

	// 測試用
	Bluetooth.prototype.getMessage = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'getMessage', []);
	}

	// 初始化藍牙
	Bluetooth.prototype.init = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'init', []);
	}

	// 建立socket
	Bluetooth.prototype.buildSocket = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'buildSocket', []);
	}

	// 取得加速度
	Bluetooth.prototype.getAcceleration = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'PluginTest', 'getAcceleration', []);
	}

	module.exports = new Bluetooth();
});