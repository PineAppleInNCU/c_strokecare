/*
Copyright (c) 2011, Sony Ericsson Mobile Communications AB
Copyright (c) 2011-2013, Sony Mobile Communications AB

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 * Neither the name of the Sony Ericsson Mobile Communications AB / Sony Mobile
 Communications AB nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.sonymobile.smartextension.hellosensors;


import com.example.sonymobile.smartextension.hellosensors.GetValue;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * The Hello Sensors Extension Service handles registration and keeps track of
 * all sensors on all accessories.
 */
@SuppressWarnings("unused")
public class HelloSensorsExtensionService extends ExtensionService {

    public static final int NOTIFY_STOP_ALERT = 1;

    public static final String LOG_TAG = "HelloSensorsExtensionService";
    
    public final String CLASS = getClass().getSimpleName();
    private String value_x,value_y,value_z;
    private String reString;
    private LocalBinder myBinder=new LocalBinder();
    private HelloSensorsExtensionService HelloSensorsExtensionService;

    public HelloSensorsExtensionService() {
        super();
        
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
    	int retVal = super.onStartCommand(intent, flags, startId);
    	
    	if(intent != null){
    		value_x = intent.getStringExtra("value_y");
        	value_y = intent.getStringExtra("value_y");
        	value_z = intent.getStringExtra("value_z");
    		if(value_x != null ||value_y != null || value_z != null ){
    			value_x = Float.toString(Float.parseFloat(intent.getStringExtra("value_x"))*(-1));
        		value_y = Float.toString(Float.parseFloat(intent.getStringExtra("value_y"))*(-1));
        		value_z = Float.toString(Float.parseFloat(intent.getStringExtra("value_z"))*(-1));
    			reString = value_x+"+"+value_y+"+"+value_z;
    		}
        	
        	
        	Log.d(HelloSensorsExtensionService.LOG_TAG, "onStartCommand: " +reString);
    	}
    	return retVal;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        
        
        Log.d(LOG_TAG, CLASS + ": onCreate");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new HelloSensorsRegistrationInformation(this);
    }

    @Override
    protected boolean keepRunningWhenConnected() {
        return false;
    }

    @Override
    public ControlExtension createControlExtension(String hostAppPackageName) {
    	Log.d(LOG_TAG, CLASS + ": createControlExtension");
        return new HelloSensorsControl(hostAppPackageName, this);
    }
    
    protected   void onNewIntent() {
   
//    	value_x = intent.getStringExtra("value_x");
//    	value_y = intent.getStringExtra("value_y");
//    	value_z = intent.getStringExtra("value_z");
    	
    	
    	reString = value_x+"+"+value_y+"+"+value_z;
    	Log.d(HelloSensorsExtensionService.LOG_TAG, "onNewIntent: " +reString);
		
		
    	
    }
    public String getValue(){
   	 
    	
    	return reString;
    }
    
    public IBinder onBind(Intent intent) {


        return myBinder;
    }
	public class LocalBinder extends Binder{
		  public HelloSensorsExtensionService getService(){
		   return HelloSensorsExtensionService.this;
		   
		  }
	}
}
