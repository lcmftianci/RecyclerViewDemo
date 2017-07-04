package com.lcmf.xll.recyclerviewdemo.DeskAssist.Service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lcmf.xll.recyclerviewdemo.DeskAssist.widgets.AssistView;
import com.lcmf.xll.recyclerviewdemo.DeskAssist.widgets.AssistView.ServiceListener;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class AssistService  extends Service implements ServiceListener {
	private Intent mIntent;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		// ´´½¨serviceÊ±Ò»¸ö ÊµÀý»¯Ò»¸öTableShowView¶ÔÏó²¢ÇÒµ÷ÓÃËûµÄfun()·½·¨°ÑËü×¢²áµ½windowManagerÉÏ
		super.onCreate();
		new AssistView(this, this).initTouchViewEvent();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mIntent = intent;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void OnCloseService(boolean isClose) {
		stopService(mIntent);
	}
}
