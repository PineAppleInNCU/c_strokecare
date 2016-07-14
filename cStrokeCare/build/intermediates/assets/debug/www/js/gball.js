
cordova.define("gball", function(require, exports, module) {
	var exec = require('cordova/exec');//define(id,factory)
	
	var gball = function() {};
	

	/**
	 * Enable bluetooth
	 * 
	 * @param successCallback function to be called when enabling of bluetooth was successfull
	 * @param errorCallback function to be called when enabling was not possible / did fail
	 */

	gball.prototype.acc = function (successCallback,failureCallback){
   							//console.log("Prima di execute!");
   							return exec(successCallback,failureCallback,'Gball','acc',[]);
  	}


	
	var balls = new gball();
	module.exports = balls;
});
