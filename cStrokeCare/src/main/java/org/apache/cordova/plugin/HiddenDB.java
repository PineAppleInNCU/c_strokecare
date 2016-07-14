package org.apache.cordova.plugin;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.cordova.database.HL_Database;
import org.apache.cordova.database.Record_DataSet;
import org.apache.cordova.c_strokecare.R;

import android.app.Activity;
import android.os.Bundle;

public class HiddenDB extends Activity{
	
	
	private HL_Database database;	
	private ArrayList<Record_DataSet> sites;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heddendbpage);
        if(database==null)
        	database = new HL_Database(this);
        
        sites = database.getAllSites_Record();
        System.out.println("size: "+sites.size());
        //System.out.println()
		/*database = new HL_Database(this);
		sites = database.getAllSites_Record();
		
		System.out.println("Adata: "+sites.get(0).getAdata());
       */

    }

   

    

    
    @Override
    protected void onResume() {
        super.onResume();
        // In order to interact with the UI thread from a broadcast receiver, we need to perform any updates through
        // an Android handler. For more information, see: http://developer.android.com/reference/android/os/Handler
        // .html
        
    }

}
