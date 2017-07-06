package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.app.Application;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ScreenCutApplication extends Application {
	Intent intent;
	int resultCode;
	private MediaProjectionManager mMediaProjectionManager;

	public MediaProjectionManager getMediaProjectionManager() {
		return mMediaProjectionManager;
	}

	public void setMediaProjectionManager(MediaProjectionManager mMediaProjectionManager) {
		this.mMediaProjectionManager = mMediaProjectionManager;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
