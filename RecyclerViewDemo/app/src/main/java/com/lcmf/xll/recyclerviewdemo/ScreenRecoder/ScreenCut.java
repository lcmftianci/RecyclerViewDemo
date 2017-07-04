package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.app.Activity;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;

import com.lcmf.xll.recyclerviewdemo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/7/3 0003.
 *View view = activity.getWindow().getDecorView();
 *Enables or disables the drawing cache
 *view.setDrawingCacheEnabled(true);
 *will draw the view in a bitmap
 *view.buildDrawingCache();
 *Bitmap bitmap = view.getDrawingCache();
 *使用Mediacodec编码，MediaMuxer封装转为MP4等格式
 *
 *
 */

public class ScreenCut extends Activity {
	MediaProjectionManager mMediaProjectionManager;
	private VirtualDisplay mVirtualDisplay;
	int mWidth;
	int mHeight;
	int mDpi;
	Surface mSurface;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_recoder);
		mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
		Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
		startActivityForResult(captureIntent, 1002);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
		if (mediaProjection == null) {
			Log.e("@@", "media projection is null");
			return;
		}else
		{
			mVirtualDisplay = mediaProjection.createVirtualDisplay(TAG + "-display",
					mWidth, mHeight, mDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
					mSurface, null, null);
		}
	}

}
