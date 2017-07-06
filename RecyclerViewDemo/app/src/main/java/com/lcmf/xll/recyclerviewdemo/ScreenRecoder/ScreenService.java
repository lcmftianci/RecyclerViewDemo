package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ScreenService extends Service {
	private MediaProjection mMediaProjection = null;
	private VirtualDisplay mVirtualDisplay = null;
	public static int mResultCode = 0;
	public static Intent mResultData = null;
	public static MediaProjectionManager mMediaProjectionManager1 = null;
	private WindowManager mWindowManager1 = null;
	private int windowWidth = 0;
	private int windowHeight = 0;
	private ImageReader mImageReader = null;
	private DisplayMetrics metrics = null;
	private int mScreenDensity = 0;
	private String strDate;
	private String pathImage;
	private String nameImage;
	private ScreenCutFloatView mFloatView;

	private java.text.SimpleDateFormat dateFormat;

	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		createFloatView();
		createVirtualEnvironment();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void createFloatView() {
		//在这里不介绍悬浮框，
		mFloatView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// hide the button
				mFloatView.setVisibility(View.INVISIBLE);

				Handler handler1 = new Handler();
				handler1.postDelayed(new Runnable() {
					public void run() {
						//start virtual
						startVirtual();
					}
				}, 500);

				Handler handler2 = new Handler();
				handler2.postDelayed(new Runnable() {
					public void run() {
						//capture the screen
						startCapture();
					}
				}, 1500);

				Handler handler3 = new Handler();
				handler3.postDelayed(new Runnable() {
					public void run() {
						mFloatView.setVisibility(View.VISIBLE);
						//stopVirtual();
					}
				}, 1000);
			}
		});

	}

	private void createVirtualEnvironment() {
		dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		strDate = dateFormat.format(new java.util.Date());
		pathImage = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
		nameImage = pathImage + strDate + ".png";
		mMediaProjectionManager1 = (MediaProjectionManager) getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		mWindowManager1 = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
		windowWidth = mWindowManager1.getDefaultDisplay().getWidth();
		windowHeight = mWindowManager1.getDefaultDisplay().getHeight();
		metrics = new DisplayMetrics();
		mWindowManager1.getDefaultDisplay().getMetrics(metrics);
		mScreenDensity = metrics.densityDpi;
		mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2); //ImageFormat.RGB_565
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void startVirtual() {
		if (mMediaProjection != null) {
			virtualDisplay();
		} else {
			setUpMediaProjection();
			virtualDisplay();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void setUpMediaProjection() {
		mResultData = ((ScreenCutApplication) getApplication()).getIntent();
		mResultCode = ((ScreenCutApplication) getApplication()).getResultCode();
		mMediaProjectionManager1 = ((ScreenCutApplication) getApplication()).getMediaProjectionManager();
		mMediaProjection = mMediaProjectionManager1.getMediaProjection(mResultCode, mResultData);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void virtualDisplay() {
		mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
				windowWidth, windowHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
				mImageReader.getSurface(), null, null);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void startCapture() {
		strDate = dateFormat.format(new java.util.Date());
		nameImage = pathImage + strDate + ".png";
		Image image = mImageReader.acquireLatestImage();
		int width = image.getWidth();
		int height = image.getHeight();
		final Image.Plane[] planes = image.getPlanes();
		final ByteBuffer buffer = planes[0].getBuffer();
		int pixelStride = planes[0].getPixelStride();
		int rowStride = planes[0].getRowStride();
		int rowPadding = rowStride - pixelStride * width;
		Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
		bitmap.copyPixelsFromBuffer(buffer);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		image.close();

		if (bitmap != null) {
			try {
				File fileImage = new File(nameImage);
				if (!fileImage.exists()) {
					fileImage.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(fileImage);
				if (out != null) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri contentUri = Uri.fromFile(fileImage);
					media.setData(contentUri);
					this.sendBroadcast(media);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void tearDownMediaProjection() {
		if (mMediaProjection != null) {
			mMediaProjection.stop();
			mMediaProjection = null;
		}
	}

	private void stopVirtual() {
		if (mVirtualDisplay == null) {
			return;
		}
		mVirtualDisplay.release();
		mVirtualDisplay = null;
	}

	@Override
	public void onDestroy() {
		// to remove mFloatLayout from windowManager
		super.onDestroy();
//        if (mFloatLayout != null) {
//            mWindowManager.removeView(mFloatLayout);
//        }
		tearDownMediaProjection();
	}
}
