package com.example.geak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.UUID;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class PluginTest extends Plugin{
	
	private static final UUID MY_UUID = UUID.fromString("b539eff8-89c2-4727-a762-14838639d73e");
	
	private static final String ACTION_TEST_MESSAGE = "getMessage";
	private static final String ACTION_BLUETOOTH_INITIALIZE = "init";
	private static final String ACTION_SOCKET = "buildSocket";
	private static final String ACTION_GET_ACCELERATION = "getAcceleration";
	
	private static final String RESULT_INIT_SUCCESS = "Initial finished";
	private static final String RESULT_NOT_SUPPORT_BT = "Device do not support bluetooth";
	private static final String RESULT_SET_ADAPTER = "Adapter has been set";
	private static final String RESULT_CONNECT_SUCCESS = "Connect succeeded";
	private static final String RESULT_CONNECT_FAIL = "Can not find geak app";
	private static final String RESULT_NOT_PAIRED = "Do not have paired device";
	
	private PluginResult return_data = null;
	private BluetoothAdapter myBluetoothAdapter = null;
	private Set<BluetoothDevice> pairedDevice = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	private MessageHandler messageHandler = null;

	@SuppressLint("NewApi")
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
//		皜祈岫�
		if(ACTION_TEST_MESSAGE.equals(action)){
			return_data = new PluginResult(PluginResult.Status.OK, "Test");
		}
//		�������
		else if(ACTION_BLUETOOTH_INITIALIZE.equals(action)){
//			�������
			myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			return_data = new PluginResult(PluginResult.Status.OK, RESULT_SET_ADAPTER);
		}
//		��岫撱箇�ocket銝血��O銝脫��
		else if(ACTION_SOCKET.equals(action)){
			pairedDevice = myBluetoothAdapter.getBondedDevices();
			if (pairedDevice.size() > 0) {
	            for (BluetoothDevice device : pairedDevice) {
	            	try {
						BluetoothSocket temp_socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
						temp_socket.connect();
						
						reader = new BufferedReader(new InputStreamReader(temp_socket.getInputStream()));
						writer = new BufferedWriter(new OutputStreamWriter(temp_socket.getOutputStream()));
						
						messageHandler = new MessageHandler(reader, writer); 
						
						return_data = new PluginResult(PluginResult.Status.OK, RESULT_CONNECT_SUCCESS);
						return return_data;
					} catch (IOException e) {
						return_data = new PluginResult(PluginResult.Status.OK, RESULT_CONNECT_FAIL);
					}
	            }
	        }else{
	        	return_data = new PluginResult(PluginResult.Status.OK, RESULT_NOT_PAIRED);
	        }
		}
//		�����漲
		else if(ACTION_GET_ACCELERATION.equals(action)){
			return_data = new PluginResult(PluginResult.Status.OK, messageHandler.getAcceleration());
		}
//		�摰���
		else{
			return_data = new PluginResult(PluginResult.Status.OK, "GG");
		}
		
		return return_data;
	}
	
	private class MessageHandler{
		private BufferedReader reader = null;
		private BufferedWriter writer = null;
		
		public MessageHandler(BufferedReader br, BufferedWriter bw){
			this.reader = br;
			this.writer = bw;
		}
		
		public String getAcceleration(){
			try {
				String temp = "";
				writer.write("get\n");
				writer.flush();
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return "IOExeption";
			}
		}
	}
}