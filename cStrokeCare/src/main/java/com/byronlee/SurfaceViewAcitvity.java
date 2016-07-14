package com.byronlee;


import org.apache.cordova.c_strokecare.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;


public class SurfaceViewAcitvity extends Activity {

    MyView mAnimView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// �撅蝷箇�
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
	//撘箏璅芸�� 
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	// �蝷箄摰��虜��iew
	mAnimView = new MyView(this);
	setContentView(mAnimView);
    }

    
    public class MyView extends SurfaceView implements Callback,Runnable ,SensorEventListener{
	 /**瘥�1撣批�銝�甈∪���**/  
	public static final int TIME_IN_FRAME = 10; 

	/** 皜豢�蝚� **/
	Paint mPaint = null;
	Paint mTextPaint = null;
	SurfaceHolder mSurfaceHolder = null;   //SurfaceHolder.Callback ��嚗恥�蝡臬隞亙�餈葵������銵券����縑���

	/** ��皜豢��敺芰 **/
	boolean mRunning = false;

	/** 皜豢�撣� **/
	Canvas mCanvas = null;

	/**��皜豢�儐�**/
	boolean mIsRunning = false;
	
	/**SensorManager蝞∠�**/
	private SensorManager mSensorMgr = null;    
	Sensor mSensor = null;    
	
	/**��撅�捐擃�**/
	int mScreenWidth = 0;
	int mScreenHeight = 0;
	
	/**撠����辣頞����**/
	private int mScreenBallWidth = 0;
	private int mScreenBallHeight = 0;
	
	/**皜豢����辣**/
	private Bitmap mbitmapBg;
	
	/*��蝸��撌虫������ ��嚗�*/
//	private Bitmap mbitmaptopBg;
	
	
	/**撠����辣**/
	private Bitmap mbitmapBall;
	
	/**撠�����蔭**/
	private float mPosX = 10;
	private float mPosY =10;
	
	/**�����頧� Y頧� Z頧渡�����**/
	private float mGX = 0;
	private float mGY = 0;
	private float mGZ = 0;
	
	public MyView(Context context) {
	    super(context);
	    /** 霈曄蔭敶�iew������ **/
	    this.setFocusable(true);
	    /** 霈曄蔭敶�iew���圻�鈭辣 **/
	    this.setFocusableInTouchMode(true);
	    /** ��SurfaceHolder撖寡情 **/
	    mSurfaceHolder = this.getHolder();
	    /** 撠SurfaceHolder瘛餃�Callback����銝� **/
	    mSurfaceHolder.addCallback(this);
	    /** ��遣�撣� **/
	    mCanvas = new Canvas();
	    /** ��遣�蝥輻蝚� **/
	    mPaint = new Paint();
	   
	    String familyName = "摰��";
	    Typeface font = Typeface.create(familyName,Typeface.NORMAL);    
	    mPaint.setColor(Color.BLACK);
	    mPaint.setTextSize(17);	    
	    mPaint.setTypeface(font);
	   
	    
	    /*��蝸��撌虫������ ��嚗�*/
	//    mbitmaptopBg=BitmapFactory.decodeResource(this.getResources(), R.drawable.top_left);
	   
	    
	    /**��蝸撠����**/
	    mbitmapBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon2);
	    /**��蝸皜豢��**/
	    mbitmapBg = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
	    
	    /**敺SensorManager撖寡情**/
	    mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);   
	    mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   
	    // 瘜典�istener嚗洵銝葵���璉�瘚�移蝖桀漲  
            //SENSOR_DELAY_FASTEST ������ ��蛹憭芸翰鈭瓷敹�蝙�
            //SENSOR_DELAY_GAME    皜豢����葉雿輻
            //SENSOR_DELAY_NORMAL  甇�撣賊�漲
            //SENSOR_DELAY_UI 	       ������漲
	    mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);  
	}
        
	private void Draw() {
	   
	    /**蝏皜豢��**/
	    mCanvas.drawBitmap(mbitmapBg,0,0, mPaint);
		
		
		  /*��蝸��撌虫������ ��嚗�*/
		// mCanvas.drawBitmap(mbitmaptopBg,0,0, mPaint);
		
		 
	    /**蝏撠��**/
	
	    mCanvas.drawBitmap(mbitmapBall, mPosX,mPosY, mPaint);
	    /**X頧� Y頧� Z頧渡�����**/
    
	    mCanvas.drawText("��頧游��� 嚗�" + mPosX, 0, 40, mPaint);
	    mCanvas.drawText("��頧游��� 嚗�" + mPosY, 0, 60, mPaint);
//	    mCanvas.drawText("Z頧港�宏���� 嚗�" + mGZ, 0, 80, mPaint);
	    
//	    mCanvas.drawText("Powered By Byronlee", 0, 150, mPaint);
	}
	

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
		int height) {

	}

	
	public void surfaceCreated(SurfaceHolder holder) {
	    /**撘�憪虜��蜓敺芰蝥輻��**/
	    mIsRunning = true;
	    new Thread(this).start();
	    /**敺敶���捐擃�**/
	    mScreenWidth = this.getWidth();
	    mScreenHeight = this.getHeight();
	    
	    /**敺撠������**/
	    mScreenBallWidth = mScreenWidth - mbitmapBall.getWidth();
	    mScreenBallHeight = mScreenHeight - mbitmapBall.getHeight();
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
	    mIsRunning = false;
	}

	
	public void run() {
	    while (mIsRunning) {

		/** ����皜豢����� **/
		long startTime = System.currentTimeMillis();

		/** �餈���瑪蝔���� **/
		//synchronized (mSurfaceHolder) {
		    /** ��敶�撣� ������ **/
		    mCanvas = mSurfaceHolder.lockCanvas();
		    Draw();
		    /** 蝏蝏��圾��蝷箏撅��� **/
		    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
		//}

		/** ����皜豢����� **/
		long endTime = System.currentTimeMillis();

		/** 霈∠�皜豢��甈⊥���神蝘 **/
		int diffTime = (int) (endTime - startTime);

		/** 蝖桐��活����銝�50撣� **/
		while (diffTime <= TIME_IN_FRAME) {
		    diffTime = (int) (System.currentTimeMillis() - startTime);
		    /** 蝥輻���� **/
		    Thread.yield();
		}

	    }

	}
	 
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	    // TODO Auto-generated method stub
	    
	}

	
	public void onSensorChanged(SensorEvent event) {
	    mGX = event.values[SensorManager.DATA_X];
	    mGY= event.values[SensorManager.DATA_Y];
	    mGZ = event.values[SensorManager.DATA_Z];

	    //餈��誑2�銝箔�悟撠�宏���敹�
	    mPosX += mGY * 3;
	    mPosY += mGX* 3;
	    
//	    mPosX = mGX;
//	    mPosY = mGY;
	    //璉�瘚���頞颲寧��
	    if (mPosX < 0) {
		mPosX = 0;
	    } 
	    
	    else if (mPosX > mScreenBallWidth) {
		mPosX = mScreenBallWidth;
	    }
	    if (mPosY < 0) {
		mPosY = 0;
	    } else if (mPosY > mScreenBallHeight) {
		mPosY = mScreenBallHeight;
	    }
	}
    }
    
    
    
    
    
    
}