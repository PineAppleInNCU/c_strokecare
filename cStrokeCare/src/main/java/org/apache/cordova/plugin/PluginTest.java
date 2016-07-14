package org.apache.cordova.plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;
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
import android.util.Log;

@SuppressWarnings("deprecation")
public class PluginTest extends Plugin {

	private static final UUID MY_UUID = UUID
			.fromString("b539eff8-89c2-4727-a762-14838639d73e");

	private static final String ACTION_TEST_MESSAGE = "getMessage";
	private static final String ACTION_BLUETOOTH_INITIALIZE = "init";
	private static final String ACTION_SOCKET = "buildSocket";
	private static final String ACTION_GET_ACCELERATION = "getAcceleration";

	private static final String RESULT_INIT_SUCCESS = "Initial finished";
	private static final String RESULT_NOT_SUPPORT_BT = "Device do not support bluetooth";
	private static final String RESULT_SET_ADAPTER = "Adapter has been set";
	private static final String RESULT_CONNECT_SUCCESS = "Connect succeeded";
	private static final String RESULT_CONNECT_FAIL = "Can not find geak app";
	private static final String RESULT_HAVE_CONNECTED = "Overlap socket";
	private static final String RESULT_NOT_PAIRED = "Do not have paired device";

	private PluginResult return_data = null;
	private BluetoothAdapter myBluetoothAdapter = null;
	private Set<BluetoothDevice> pairedDevice = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	private MessageHandler messageHandler = null;
	private MessageHandler2 messageHandler2 = null;
	private boolean isConnect = false;
	private boolean isConnect2 = false;
	private ConnectThread connectedThread= null;

	@SuppressLint("NewApi")
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		// ���疵嚙踐��
		if (ACTION_TEST_MESSAGE.equals(action)) {
			return_data = new PluginResult(PluginResult.Status.OK, "Test");
		}
		// 嚙踝蕭豲蕭��蕭謘選蕭��蕭嚙�
		else if (ACTION_BLUETOOTH_INITIALIZE.equals(action)) {
			// 嚙踝蕭謘潘蕭謅蕭��蕭謕遛嚙踝蕭����
			myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			return_data = new PluginResult(PluginResult.Status.OK,
					RESULT_SET_ADAPTER);
		}
		// 嚙踝蕭謅疵�蝞蕭��cket���嚙踐□嚙踐���嚙踝蕭
		else if (ACTION_SOCKET.equals(action)) {
			//if (!isConnect) {
				myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				pairedDevice = myBluetoothAdapter.getBondedDevices();
				if (pairedDevice.size() > 0) {
					for (BluetoothDevice device : pairedDevice) {
						/*
						try {
							BluetoothSocket temp_socket = device
									.createInsecureRfcommSocketToServiceRecord(MY_UUID);
							temp_socket.connect();

							reader = new BufferedReader(new InputStreamReader(
									temp_socket.getInputStream()));
							writer = new BufferedWriter(new OutputStreamWriter(
									temp_socket.getOutputStream()));
							
							if(messageHandler == null){
								messageHandler = new MessageHandler(reader, writer); 
								//isConnect = true;
							}

							else if (messageHandler2 == null){
								messageHandler2 = new MessageHandler2(reader, writer); 
								//isConnect = true;
							}
							
							return_data = new PluginResult(
									PluginResult.Status.OK,
									RESULT_CONNECT_SUCCESS);
							
							return return_data;
						} catch (SocketException e) {
							return_data = new PluginResult(
									PluginResult.Status.OK,
									RESULT_HAVE_CONNECTED);
						} catch (IOException e) {
							return_data = new PluginResult(
									PluginResult.Status.OK, RESULT_CONNECT_FAIL);
						}*/
						
						connectedThread =  new ConnectThread(device);
						connectedThread.start();
					}
					return_data = new PluginResult(
							PluginResult.Status.OK,
							RESULT_CONNECT_SUCCESS);
					
				} else {
					return_data = new PluginResult(PluginResult.Status.OK,
							RESULT_NOT_PAIRED);
				}
			//}
		}
		// 嚙踝蕭謘潘蕭謅蕭蹎ｇ蕭賹撞
		else if (ACTION_GET_ACCELERATION.equals(action)) {
			if (messageHandler2 == null){
				return_data = new PluginResult(PluginResult.Status.OK,
					messageHandler.getAcceleration());
			}
			else{
				return_data = new PluginResult(PluginResult.Status.OK,
					messageHandler.getAcceleration()+"+"+messageHandler2.getAcceleration());
			}
		}
		// 嚙踝���嚙踝嚙踝蕭
		else {
			return_data = new PluginResult(PluginResult.Status.OK, "GG");
		}

		return return_data;
	}
	
	private class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;
	 
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;

	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	        } catch (IOException e) { }
	        mmSocket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        myBluetoothAdapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	            Log.i("Connect", "OK");
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	        	Log.i("Connect", "Nope");
	            cancel();
	            return;
	        }
	 
	        // Do work to manage the connection (in a separate thread)
	        try {
				reader = new BufferedReader(new InputStreamReader(
						mmSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new BufferedWriter(new OutputStreamWriter(
						mmSocket.getOutputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(messageHandler == null){
				messageHandler = new MessageHandler (reader,writer);
			}
			else if(messageHandler != null && messageHandler2 ==null){
				messageHandler2 = new MessageHandler2 (reader,writer);
			}
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}

	private class MessageHandler {
		private BufferedReader reader = null;
		private BufferedWriter writer = null;

		public MessageHandler(BufferedReader br, BufferedWriter bw) {
			this.reader = br;
			this.writer = bw;
		}

		public String getAcceleration() {
			try {
				String temp = "";
				writer.write("get\n");
				writer.flush();
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				isConnect = false;
				return "IOExeption";
			}
		}
	}
	
	private class MessageHandler2{
		private BufferedReader reader = null;
		private BufferedWriter writer = null;
		
		public MessageHandler2(BufferedReader br, BufferedWriter bw){
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
				isConnect = false;
				return "IOExeption";
			}
		}
	}
}