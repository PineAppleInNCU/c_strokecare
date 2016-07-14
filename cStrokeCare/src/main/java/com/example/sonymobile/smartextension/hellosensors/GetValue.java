package com.example.sonymobile.smartextension.hellosensors;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import com.example.sonymobile.smartextension.hellosensors.HelloSensorsExtensionService;
import com.example.sonymobile.smartextension.hellosensors.HelloSensorsExtensionService.LocalBinder;

import org.json.JSONArray;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

@SuppressWarnings("deprecation")
public class GetValue extends Plugin {
	private static final String ACTION_PASSVALUE = "passValue";
	private PluginResult pluginResult = null;
	private static String resultFunction;
	private HelloSensorsExtensionService HelloSensorsExtensionService = null;
	private String reString;
	private int i = 0;

	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		if (ACTION_PASSVALUE.equals(action)) {
			StartPebbleService();
			resultFunction = getValueXYZ();
			Log.d(HelloSensorsExtensionService.LOG_TAG, "PluginResult: "
					+ reString);
			pluginResult = new PluginResult(PluginResult.Status.OK,
					resultFunction);
		}
		return pluginResult;
	}

	public String getValueXYZ() {
		if (HelloSensorsExtensionService != null) {
			System.out.println("exist");
			reString = HelloSensorsExtensionService.getValue();
			return reString = HelloSensorsExtensionService.getValue();
		}
		return "not connect";
	}

	public void StartPebbleService() {
		if (i == 0) {
			Context context = this.cordova.getActivity()
					.getApplicationContext();
			Intent intent = new Intent(context,
					HelloSensorsExtensionReceiver.class);
			context.sendBroadcast(new Intent(
					"com.sonyericsson.extras.liveware.aef.registration.EXTENSION_REGISTER_REQUEST"));
			i++;
		} else {
			Intent intent = new Intent(cordova.getActivity()
					.getApplicationContext(),
					HelloSensorsExtensionService.class);
			cordova.getActivity().startService(intent);
			cordova.getActivity().bindService(intent, mServConn,
					Activity.BIND_AUTO_CREATE);
		}
	}

	private ServiceConnection mServConn = new ServiceConnection() {
		// bind Service�L�{��,�t�η|�I�s����H�U�{���X
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			HelloSensorsExtensionService = ((LocalBinder) service).getService();
		}

		// Service�o�ͷN�~�~�|����
		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

}
