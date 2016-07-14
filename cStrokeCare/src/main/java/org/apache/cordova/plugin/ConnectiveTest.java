package org.apache.cordova.plugin;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.cordova.c_strokecare.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;





import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

public class ConnectiveTest extends Activity {
	
	//test------------------------------------------
	private Button btn;
	private int flag = 0;
	private Intent intentNew = null;
	private Context context = this;
	//================================================
	private int count_for_action=0;
    private PebbleKit.PebbleDataReceiver dataReceiver;
    private PebbleKit.PebbleAckReceiver ackReceiver;
    private PebbleKit.PebbleNackReceiver nackReceiver;

    private static final int START_KEY = 0;
    private static final int STOP_KEY = 1;
    private static final int FINISH_KEY = 2;
    private static final int CATCH_KEY = 3;
    private static final int VIBE_KEY = 4;
    
    private static final int X_KEY = 5;
    private static final int Y_KEY = 6;
    private static final int Z_KEY =7;
    
    private static final UUID TODO_LIST_UUID = UUID.fromString("1f3599a5-cf96-4c09-bbea-aa113e8cc18f");

    
    
    private final MessageManager messageManager = new MessageManager();
    
    private Button btn_start_test;
    private Button btn_vibe_test;
    
    private TextView txt_received_test;
    private TextView txt_send_test;
    private TextView txt_xvalue_test;
    private TextView txt_yvalue_test;
    private TextView txt_zvalue_test;
    
    /**
     * Manages a thread-safe message queue using a Looper worker thread to complete blocking tasks.
     */
    public class MessageManager implements Runnable {
        public Handler messageHandler;
        private final BlockingQueue<PebbleDictionary> messageQueue = new LinkedBlockingQueue<PebbleDictionary>();
        private Boolean isMessagePending = Boolean.valueOf(false);

        @Override
        public void run() {
            Looper.prepare();
            messageHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.w(this.getClass().getSimpleName(), "Please post() your blocking runnables to Mr Manager, " +
                            "don't use sendMessage()");
                }

            };
            Looper.loop();
        }

        private void consumeAsync() {
            messageHandler.post(new Runnable() {
                @Override
                public void run() {
                    synchronized (isMessagePending) {
                        if (isMessagePending.booleanValue()) {
                            return;
                        }

                        synchronized (messageQueue) {
                            if (messageQueue.size() == 0) {
                                return;
                            }
                            PebbleKit.sendDataToPebble(getApplicationContext(), TODO_LIST_UUID, messageQueue.peek());
                        }

                        isMessagePending = Boolean.valueOf(true);
                    }
                }
            });
        }

        public void notifyAckReceivedAsync() {
            messageHandler.post(new Runnable() {
                @Override
                public void run() {
                    synchronized (isMessagePending) {
                        isMessagePending = Boolean.valueOf(false);
                    }
                    messageQueue.remove();
                }
            });
            consumeAsync();
        }

        public void notifyNackReceivedAsync() {
            messageHandler.post(new Runnable() {
                @Override
                public void run() {
                    synchronized (isMessagePending) {
                        isMessagePending = Boolean.valueOf(false);
                    }
                }
            });
            consumeAsync();
        }

        public boolean offer(final PebbleDictionary data) {
            final boolean success = messageQueue.offer(data);

            if (success) {
                consumeAsync();
            }

            return success;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivetest_page);

        //todoListAdapter = TodoListAdapter.createTodoListAdapter(getApplicationContext());

        // Populate list before starting app on the Pebble
        //String[] dummyItems = getResources().getStringArray(R.array.jazz_artist_names);
        //todoListAdapter.addItems(dummyItems);
        txt_received_test=(TextView)findViewById(org.apache.cordova.c_strokecare.R.id.txt_received_test);
        txt_send_test=(TextView)findViewById(org.apache.cordova.c_strokecare.R.id.txt_send_test);
        txt_xvalue_test=(TextView)findViewById(R.id.txt_xvalue_test);
        txt_yvalue_test=(TextView)findViewById(R.id.txt_yvalue_test);
        txt_zvalue_test=(TextView)findViewById(R.id.txt_zvalue_test);
        btn_vibe_test=(Button)findViewById(R.id.btn_vibe_test);
        btn_start_test = (Button)findViewById(R.id.btn_start_test);
        
        btn_start_test.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//start
				
				PebbleDictionary data = new PebbleDictionary();
				if(count_for_action%2!=0)
				{
					
					data.addUint8(START_KEY, (byte) count_for_action);
					txt_send_test.setText("Send(START_KEY): "+count_for_action);
					
	                messageManager.offer(data);
	                count_for_action++;
	                
	                btn_start_test.setText("Start");
				}
				else//stop
				{
					data.addUint8(STOP_KEY, (byte) count_for_action);
					System.out.println("Send(STOP_KEY): "+count_for_action);
					
					messageManager.offer(data);
					count_for_action++;
					btn_start_test.setText("Stop");
				}
				
				
			}});
        
        intentNew = this.getIntent();
        btn_vibe_test.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				/*PebbleDictionary data = new PebbleDictionary();
				data.addUint8(VIBE_KEY, (byte) count_for_action);
				
				
                messageManager.offer(data);*/
				flag = 1;
				Log.v("FLAG","1 IN PLUGIN");
		        //intentNew = ((Activity) context).getIntent();
				
	            intentNew.putExtra("returnedData", Integer.toString(flag));
	            
	            if (getParent() == null) {
	            	setResult(RESULT_OK, intentNew);
	            }
	            else {
	            	getParent().setResult(RESULT_OK, intentNew);
	            }
	            finish();
	           
			}
        	
        });
        //System.out.println("This is getApplicationContext: "+getApplicationContext());
        PebbleKit.startAppOnPebble(getApplicationContext(), TODO_LIST_UUID);
        
    }

    @Override
    public void onStart() {
        // FIXME do I need to do any cleanup in onStop()?
        super.onStart();
        new Thread(messageManager).start();

    }

    

    @Override
    protected void onPause() {
        super.onPause();

        // Always deregister any Activity-scoped BroadcastReceivers when the Activity is paused
        if (dataReceiver != null) {
            unregisterReceiver(dataReceiver);
            dataReceiver = null;
        }

        if (ackReceiver != null) {
            unregisterReceiver(ackReceiver);
            ackReceiver = null;
        }

        if (nackReceiver != null) {
            unregisterReceiver(nackReceiver);
            nackReceiver = null;
        }

        //dialogManager.dismissAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // In order to interact with the UI thread from a broadcast receiver, we need to perform any updates through
        // an Android handler. For more information, see: http://developer.android.com/reference/android/os/Handler
        // .html
        final Handler handler = new Handler();

        // To receive data back from a watch-app, android
        // applications must register a "DataReceiver" to operate on the
        // dictionaries received from the watch.
        dataReceiver = new PebbleKit.PebbleDataReceiver(TODO_LIST_UUID) {
            @Override
            public void receiveData(final Context context, final int transactionId, final PebbleDictionary data) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // All data received from the Pebble must be ACK'd, otherwise you'll hit time-outs in the
                        // watch-app which will cause the watch to feel "laggy" during periods of frequent
                        // communication.
                        PebbleKit.sendAckToPebble(context, transactionId);

                        if (!data.iterator().hasNext()) {
                            return;
                        }

                       

                        final Long startValue = data.getUnsignedInteger(START_KEY);
                        
                        if (startValue != null) {
                            final int value = startValue.intValue();
                            //System.out.println("!!:"+startValue);
                            txt_received_test.setText("Receive(START_KEY): " + value + "(count = "+count_for_action+")");;
                            System.out.println("Receive(START_KEY): " + value + "(count = "+count_for_action+")");
                            
                            
                            count_for_action++;
                            btn_start_test.setText("Start");
                      
                        }

                        final Long stopValue = data.getUnsignedInteger(STOP_KEY);
                        if (stopValue != null) {
                            final int value = stopValue.intValue();
                            txt_send_test.setText("Receive(STOP_KEY): " + value + "(count = "+count_for_action+")");
                            System.out.println("Receive(STOP_KEY): " + value + "(count = "+count_for_action+")");
                            count_for_action++;
                            btn_start_test.setText("Stop");
                        }
                        
                        final Long finishValue = data.getUnsignedInteger(FINISH_KEY);
                        if (finishValue != null) {
                            final int value = finishValue.intValue();
                            System.out.println("Receive(FINISH_KEY): " + value + "(count = "+count_for_action+")");
                            count_for_action++;
                            btn_start_test.setText("Finish");
                        }
                        
                        final String XValue = data.getString(X_KEY);
                        if (XValue != null) {
                            final String value = XValue.toString();
                            //System.out.println("Receive(FINISH_KEY): " + value + "(count = "+count_for_action+")");
                            txt_xvalue_test.setText("X: "+value);
                            
                        }
                        final String YValue = data.getString(Y_KEY);
                        if (YValue != null) {
                            final String value = YValue.toString();
                            //System.out.println("Receive(FINISH_KEY): " + value + "(count = "+count_for_action+")");
                            txt_yvalue_test.setText("Y: "+value);
                            
                        }
                        final String ZValue = data.getString(Z_KEY);
                        if (ZValue != null) {
                            final String value = ZValue.toString();
                            //System.out.println("Receive(FINISH_KEY): " + value + "(count = "+count_for_action+")");
                            txt_zvalue_test.setText("Z: "+value);
                            
                        }
                        
                        
                        
                        
                    }
                });
            }
        };

        PebbleKit.registerReceivedDataHandler(this, dataReceiver);

        ackReceiver = new PebbleKit.PebbleAckReceiver(TODO_LIST_UUID) {
            @Override
            public void receiveAck(final Context context, final int transactionId) {
                messageManager.notifyAckReceivedAsync();
            }
        };

        PebbleKit.registerReceivedAckHandler(this, ackReceiver);


        nackReceiver = new PebbleKit.PebbleNackReceiver(TODO_LIST_UUID) {
            @Override
            public void receiveNack(final Context context, final int transactionId) {
                messageManager.notifyNackReceivedAsync();
            }
        };

        PebbleKit.registerReceivedNackHandler(this, nackReceiver);
    }

}
