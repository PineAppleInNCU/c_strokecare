package org.apache.cordova.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class ListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
    	Log.i("Service", "Service");
        if (messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            // Broadcast message to wearable activity for display
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }

        else if (messageEvent.getPath().equals("/message_path2")) {
            final String message = new String(messageEvent.getData());
            // Broadcast message to wearable activity for display
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message2", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }

        else {
            super.onMessageReceived(messageEvent);
        }
    }
}