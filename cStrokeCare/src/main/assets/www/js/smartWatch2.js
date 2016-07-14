
cordova.define("smartWatch2", function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var smartWatch2 = function() {};
	

	/**
	 * Enable bluetooth
	 * 
	 * @param successCallback function to be called when enabling of bluetooth was successfull
	 * @param errorCallback function to be called when enabling was not possible / did fail
	 */

	smartWatch2.prototype.passValue = function (successCallback,failureCallback){
   							//console.log("Prima di execute!");
   							return exec(successCallback,failureCallback,'GetValue','passValue',[]);
  	}

	smartWatch2.prototype.sendValue = function (successCallback,failureCallback){
   							//console.log("Prima di execute!");
   							return exec(successCallback,failureCallback,'GetValue','sendValue',[]);
  	}
	
	var SmartWatch2 = new smartWatch2();
	module.exports = SmartWatch2;
});
