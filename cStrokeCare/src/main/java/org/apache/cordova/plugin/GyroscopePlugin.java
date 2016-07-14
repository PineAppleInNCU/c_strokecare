package org.apache.cordova.plugin;



import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;

//import org.dartlang.phonegap.gyroscope.GyroscopeListener;
import org.json.JSONArray;



import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


@SuppressWarnings("deprecation")
public class GyroscopePlugin extends Plugin implements SensorEventListener {
	private static final String ACTION_TEST_MESSAGE = "getMessage";
	private static final String ACTION_GET_MESSAGE = "getGyroscopeValue";
	
	private SensorManager sensorManager; // Sensor manager
	private Sensor mSensor;
	
	private float[] sensorArray = new float[3];
	
	private PluginResult return_data = null;
	private int accuracy;
	@SuppressLint("NewApi")
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		//sensorArray[0]=(float) 1.0;
		// TODO Auto-generated method stub
		if (ACTION_TEST_MESSAGE.equals(action)) {
			return_data = new PluginResult(PluginResult.Status.OK, "Test");
			
		} else if (ACTION_GET_MESSAGE.equals(action)) {
			// List<Sensor> list =
			// this.sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
			//sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			//mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			//sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),RATE);
			  // Clear any current registrations
			 this.sensorManager = (SensorManager) cordova.getActivity().getSystemService(Context.SENSOR_SERVICE);
			 this.mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			 this.sensorManager.registerListener(this, this.mSensor, SensorManager.SENSOR_DELAY_UI);
//			List<Sensor> list = this.sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
//	        // If found, then register as listener
//	        if ((list != null) && (list.size() > 0)) {
//	          this.mSensor = list.get(0);
//	          this.sensorManager.registerListener(this, this.mSensor, SensorManager.SENSOR_DELAY_UI);
//	          //this.setStatus(GyroscopeListener.STARTING);
//	        } else {
//	         
//		}
			String str = new String(sensorArray[0] + "+" + sensorArray[1] + "+"+ sensorArray[2]);
			return_data = new PluginResult(PluginResult.Status.OK, str);
		}
		return return_data;

	}

	

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
			//System.out.println("gg");
		 switch (event.sensor.getType())
	        {

	            case Sensor.TYPE_ACCELEROMETER:
	                
	                break;
	            case Sensor.TYPE_MAGNETIC_FIELD:
	                
	                break;
	 
	            case Sensor.TYPE_GYROSCOPE:
	            	if (this.accuracy >= SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM) {
	            		float[] value = event.values;
		            	if(value[0]<=0.01&&value[0]>=-0.01){
		            		sensorArray[0] =0;
		            	}else{
		            		sensorArray[0] = value[0];
		            	}
		            	if(value[1]<=0.01&&value[1]>=-0.01){
		            		sensorArray[1] = 0;
		            	}else{
		            		sensorArray[1] = value[1];
		            	}
		            	if(value[2]<=0.01&&value[2]>=-0.01){
		            		sensorArray[2] = 0;
		            	}else{
		            		sensorArray[2] = value[2];
		            	}
	            	}
	            	
	    			
	    			
	        }
		

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		this.accuracy = accuracy;
	}

}
