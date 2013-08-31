package com.zheng.action;

import java.util.Calendar;
import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private LinearLayout mTimeline;
	private String mDate;

	private ActionReceiver mActionReceiver;
	private Intent mServiceIntent;
	private IntentFilter mIntentFilter;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTimeline = (LinearLayout)findViewById(R.id.timeline);
		
		mActionReceiver = new ActionReceiver();
		mIntentFilter = new IntentFilter(MonitorApp.ACTION);
		registerReceiver(mActionReceiver, mIntentFilter);
		
		mServiceIntent = new Intent(this, MonitorApp.class);
		startService(mServiceIntent);
		mContext = this;
	}


	public class ActionReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.v("zhengwenhui", action);
			switch (getAction(action)) {
			case 0:
				Set<String> Categories = intent.getCategories();
				for (String string : Categories) {
					Log.i("zhengwenhui", string);
					Button view = new Button(mContext);
					//view.setBackgroundResource(android.R.color.darker_gray);
					view.setText(string);
					mTimeline.addView(view);
				}
				break;
			case 1:

				break;
			case 2:
				String app = intent.getStringExtra("package_name");
				mDate = Calendar.getInstance().getTime().toString();
				Log.i("zhengwenhui", app);
				Log.i("zhengwenhui", mDate);
				/*mMessage.append(mDate);
				mMessage.append(" ");
				mMessage.append(app);
				mMessage.append("\n");*/
				
				Button view = new Button(mContext);
				//view.setBackgroundResource(android.R.color.darker_gray);
				view.setText(app);
				mTimeline.addView(view);
				break;

			default:
				break;
			}
		}

		String [] actions={
				Intent.ACTION_MAIN,
				Intent.ACTION_DEFAULT,
				MonitorApp.ACTION,
		};

		private int getAction(String ac){
			int i=0;
			for (; i<actions.length; i++) {
				if(ac.equals(actions[i])){
					break;
				}
			}
			return i;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(mServiceIntent);
		unregisterReceiver(mActionReceiver);
		super.onDestroy();
	}
}
