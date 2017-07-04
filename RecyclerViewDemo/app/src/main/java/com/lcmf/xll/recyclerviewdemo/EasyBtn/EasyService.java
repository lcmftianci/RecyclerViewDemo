package com.lcmf.xll.recyclerviewdemo.EasyBtn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class EasyService extends Service implements SoEasyView.ServiceListener{
	private Intent mIntent;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void OnCloseService(boolean isClose) {
		stopService(mIntent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mIntent = intent;
		return super.onStartCommand(intent, flags, startId);
	}

	public void onCreate() {
		// 创建service时一个 实例化一个TableShowView对象并且调用他的fun()方法把它注册到windowManager上
		super.onCreate();
		new SoEasyView(this, this).initSoEasyTouchEvent();
	}


}
