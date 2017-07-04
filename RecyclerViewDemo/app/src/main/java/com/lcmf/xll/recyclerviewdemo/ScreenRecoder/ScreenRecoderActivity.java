package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.lcmf.xll.recyclerviewdemo.R;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.Manifest;

/**
 * Created by Administrator on 2017/7/3 0003.
 *1：获取MediaProjectionManager
 *2：通过MediaProjectionManager.createScreenCaptureIntent()获取Intent
 *3：通过startActivityForResult传入Intent然后在onActivityResult中通过MediaProjectionManager.getMediaProjection(resultCode,data)获取MediaProjection
 *4：创建ImageReader,构建VirtualDisplay
 *5:最后就是通过ImageReader截图，就可以从ImageReader里获得Image对象。
 *6:将Image对象转换成bitmap
 */

public class ScreenRecoderActivity extends Activity {
	private static final int RECORDER_CODE = 0;
	private static final String TAG = "ScreenRecoderActivity";
	//1
	MediaProjectionManager mpManager;
	MediaProjection mProjection;
	MediaCodec mCodec;
	MediaMuxer mMuxer;
	int mWidth;
	int mHeight;
	int mDpi;
	Surface mSurface;
	VirtualDisplay mVirtualDisplay;
	private MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
	private int videoTrackIndex = -1;
	String filePath;
	private AtomicBoolean mQuit = new AtomicBoolean(false);
	private boolean muxerStart = false;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_recoder);

		if(ContextCompat.checkSelfPermission(ScreenRecoderActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(ScreenRecoderActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
		}

		//获取屏幕信息
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		mWidth = metrics.widthPixels;
//		mHeight = metrics.heightPixels;
//		mDpi = metrics.densityDpi;

		mWidth = 720;
		mHeight = 1280;
		mDpi = 1;

		//文件管理
		File file = new File(Environment.getExternalStorageDirectory(), "recond-" + mWidth + "x" +
		mHeight + "-" + System.currentTimeMillis());
		filePath = file.getAbsolutePath();

		mpManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
		//Intent CapIntent = mpManager.createScreenCaptureIntent();
		//startActivityForResult(CapIntent,RECORDER_CODE);
//		Button btn = (Button)findViewById(R.id.id_start);
//		btn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				StartRecorder(v);
//			}
//		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch(requestCode)
		{
			case 1:
				if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{

				}
				else
				{
					Toast.makeText(ScreenRecoderActivity.this, "权限失败", Toast.LENGTH_SHORT).show();
				}
		}
	}

	//返回值
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mProjection = mpManager.getMediaProjection(resultCode, data);
		new Thread(){
			@Override
			public void run() {
				try {
					try{
						prepareEncoder();
						mMuxer = new MediaMuxer(filePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
					}catch (IOException e){
						throw new RuntimeException(e);
					}
					mVirtualDisplay = mProjection.createVirtualDisplay(TAG + "-display",
							mWidth, mHeight, mDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
							mSurface, null, null);
					recordVirtualDisplay();
				}finally {
					release();
				}
			}
		}.start();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void recordVirtualDisplay() {
		while (!mQuit.get()) {
			int index = mCodec.dequeueOutputBuffer(bufferInfo, 10000);
			if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
				resetOutputFormat();
			} else if (index >= 0) {
				encodeToVideoTrack(index);
				mCodec.releaseOutputBuffer(index, false);
			}
		}
	}

	private void encodeToVideoTrack(int index) {
		ByteBuffer encodedData = mCodec.getOutputBuffer(index);

		if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
			bufferInfo.size = 0;
		}
		if (bufferInfo.size == 0) {
			encodedData = null;
		}
		if (encodedData != null) {
			encodedData.position(bufferInfo.offset);
			encodedData.limit(bufferInfo.offset + bufferInfo.size);
			mMuxer.writeSampleData(videoTrackIndex, encodedData, bufferInfo);
		}
	}

	private void resetOutputFormat() {
		MediaFormat newFormat = mCodec.getOutputFormat();
		videoTrackIndex = mMuxer.addTrack(newFormat);
		mMuxer.start();
		muxerStart = true;
	}

	public void StartRecorder(View view) {
		startActivityForResult(mpManager.createScreenCaptureIntent(),RECORDER_CODE);
		Toast.makeText(ScreenRecoderActivity.this, "开始录屏", Toast.LENGTH_SHORT).show();
	}

	public void StopRecorder(View view) {
		mQuit.set(true);
		Toast.makeText(this, "Recorder stop", Toast.LENGTH_SHORT).show();
	}

	private void release() {
		if (mCodec != null) {
			mCodec.stop();
			mCodec.release();
			mCodec = null;
		}
		if (mVirtualDisplay != null) {
			mVirtualDisplay.release();
		}
		if (mProjection != null) {
			mProjection.stop();
		}
		if (mMuxer != null) {
			mMuxer.release();
			mMuxer = null;
		}
	}

	private void prepareEncoder() throws IOException {
		MediaFormat format = MediaFormat.createVideoFormat("video/avc", mWidth, mHeight);
		format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
				MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
		format.setInteger(MediaFormat.KEY_BIT_RATE, 6000000);
		format.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
		format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10);

		mCodec = MediaCodec.createEncoderByType("video/avc");
		mCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
		mSurface = mCodec.createInputSurface();
		mCodec.start();
	}
}


