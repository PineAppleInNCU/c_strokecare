package com.byronlee;

import org.apache.cordova.c_strokecare.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BalanceBallActivity extends Activity {
    Context mContext = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // �撅蝷箇�
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	//撘箏璅芸�� 
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        
	setContentView(R.layout.main_gball);
	mContext = this;
        
	/**餈皜豢���� - 撠�宏�**/
        Button botton0 = (Button)findViewById(R.id.button0);
        botton0.setOnClickListener(new OnClickListener() {
	
	    public void onClick(View arg0) {
		 Intent intent = new Intent(mContext,SurfaceViewAcitvity.class); 
		 startActivity(intent);
	    }
	});
	
    }
}