package org.apache.cordova.plugin;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.*;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

@SuppressWarnings("deprecation")
public class bt extends Plugin{
	private PluginResult return_data = null;
	private BluetoothAdapter mBluetoothAdapter;
	@Override
	public PluginResult execute(String action, JSONArray args,
			String callbackId) {
		if(action.equals("get_btstatus")){
			mBluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
			
			if (!mBluetoothAdapter.isEnabled()){//判斷目前bluetooth狀態 開啟會回傳true
				return_data = new PluginResult(PluginResult.Status.OK, "Enabling Bluetooth...");
				mBluetoothAdapter.enable();
			}
			else{
				return_data = new PluginResult(PluginResult.Status.OK, "Bluetooth has already enabled");
			    // mBluetoothAdapter.enable();  //將buletooth開啟
			}
			
			return return_data;
		}
		return return_data;
	}
}
