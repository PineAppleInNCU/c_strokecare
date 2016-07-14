cordova.define('cordova/plugin/GyroscopePlugin', function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var Bluetooth = function() {};

	// 測試用
	Bluetooth.prototype.getMessage = function(successCallback, failureCallback) {
		console.log("Prima di execute!");
		return exec(successCallback, failureCallback, 'GyroscopePlugin', 'getMessage', []);
	}

	// // 初始化藍牙
	// Bluetooth.prototype.init = function(successCallback, failureCallback) {
	// 	return exec(successCallback, failureCallback, 'GyroscopePlugin', 'init', []);
	// }

	// // 建立socket
	// Bluetooth.prototype.buildSocket = function(successCallback, failureCallback) {
	// 	return exec(successCallback, failureCallback, 'GyroscopePlugin', 'buildSocket', []);
	// }

	// 取得加速度
	Bluetooth.prototype.getGyroscopeValue = function(successCallback, failureCallback) {
		return exec(successCallback, failureCallback, 'GyroscopePlugin', 'getGyroscopeValue', []);
	}

	module.exports = new Bluetooth();
});