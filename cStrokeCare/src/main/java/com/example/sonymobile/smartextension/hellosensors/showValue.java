package com.example.sonymobile.smartextension.hellosensors;

import org.apache.cordova.c_strokecare.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class showValue extends Activity{
	private TextView textView1,textView2,textView3;
	private Button button1;
	private Intent intent;
	private String value_x,value_y,value_z;
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.showvalue);
//	        textView1 = (TextView)findViewById(R.id.textView1);
//	        textView2 = (TextView)findViewById(R.id.textView2);
//	        textView3 = (TextView)findViewById(R.id.textView3);
//	        button1 =(Button)findViewById(R.id.button1);
	        intent = this.getIntent();
	        onNewIntent(intent);
	   }    
	    protected void onNewIntent(Intent intent) {
	    	value_x = intent.getStringExtra("value_x");
	    	value_y = intent.getStringExtra("value_y");
	    	value_z = intent.getStringExtra("value_z");
//	    	textView1.setText(value_x);
//	    	textView2.setText(value_y);
//	    	textView3.setText(value_z);
	        Log.d(HelloSensorsExtensionService.LOG_TAG, "onNewIntent: " + value_x);
	    }
	    public String getValue(){
	    	String reString  = "0"+"+"+"0"+"+"+"0";
	    	
	    	return reString;
	    }

}
