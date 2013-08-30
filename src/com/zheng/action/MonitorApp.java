package com.zheng.action;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//Test,done tomrrow
public class MonitorApp extends Service {
	public static final String ACTION="com.zheng.action.APPS_CHANGED";
	private ActivityManager am=null;

	private String mLastTopActivity;
	private String mCurTopActivity;


	public String getCurRunningActivityPackageName() {
		RunningTaskInfo info = am.getRunningTasks(1).get(0);
		return info.topActivity.getPackageName();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		am=(ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		Log.i("service", "start");
	}

	@Override
	public void onStart(Intent intent, int startId) {

		Thread th_monitor=new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);//–›√ﬂ“ª∂Œ ±º‰
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					mCurTopActivity = getCurRunningActivityPackageName();

					if(!mCurTopActivity.equals(mLastTopActivity)){
						mLastTopActivity = mCurTopActivity;

						Intent intent=new Intent(ACTION);
						intent.putExtra("package_name", mCurTopActivity);
						sendBroadcast(intent);
					}
				}
			}
		});
		th_monitor.start();
	}
}

