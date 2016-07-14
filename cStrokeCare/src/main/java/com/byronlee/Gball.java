package com.byronlee;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Gball extends Plugin{
	Context mContext = null;
	private static final String ACTION_STARTS = "starts";
	private static String resultFunction ;
	private PluginResult pluginResult = null;
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		Log.d("Gball", "Action: " + action);
		if( ACTION_STARTS.equals(action))
		{
			Context context=this.cordova.getActivity().getApplicationContext();
//			Intent intent = new Intent(cordova.getActivity()
//					.getApplicationContext(), BalanceBallActivity.class);
			Intent intent=new Intent(context,BalanceBallActivity.class);

		    context.startActivity(intent);
			
			pluginResult = new PluginResult(PluginResult.Status.OK, resultFunction);
		}
		return pluginResult;
	}

}
