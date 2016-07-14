package org.apache.cordova.plugin;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class AndroidWear extends Plugin{
	private String string_from_watch_1 = null;
	private String string_from_watch_2 = null;
	private PluginResult return_data = null;
	private static final String ACTION_ANDROIDWEAR_START = "start";
	
	private static final String RESULT_WATCH_ONE_OFFLINE = "Watch one offline";

    @Override
    public PluginResult execute(String action, JSONArray args, String callbackId) {

        if (ACTION_ANDROIDWEAR_START.equals(action)) {
			IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
			MessageReceiver messageReceiver = new MessageReceiver();
			LocalBroadcastManager.getInstance(this.cordova.getActivity().getApplicationContext()).registerReceiver(messageReceiver, messageFilter);

			if(string_from_watch_1 == null){
				return_data = new PluginResult(PluginResult.Status.OK, RESULT_WATCH_ONE_OFFLINE);
			}
			else{
				return_data = new PluginResult(PluginResult.Status.OK, string_from_watch_1+"+"+string_from_watch_2);
			}
        }
		return return_data; 
		
    }
	
	public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String message2 = intent.getStringExtra("message2");
            // Display message in UI

            if(message!=null){
                string_from_watch_1 = message;
            }

            if(message2!=null){
                string_from_watch_2 = message2;
            }

        }
    }
}
