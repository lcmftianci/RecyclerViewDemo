package com.lcmf.xll.recyclerviewdemo.EasyBtn;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class SoEasy extends Activity {

	private Button mShowViewBtn = null;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.easy_btn_activity);
		initEvent();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			// 使用api11 新加 api
			requestDrawOverLays();
		}
	}
	private void initEvent() {
		initViews();
		setViews();
	}

	private void initViews() {
		mShowViewBtn = (Button) findViewById(R.id.activity_main_show_touchview_button);
	}

	private void setViews() {
		mShowViewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ContextCompat.checkSelfPermission(SoEasy.this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(SoEasy.this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 1);
				}else
				{
					startEasyService(null);
				}
			}
		});
	}

	public void startEasyService(View v) {
		startService(new Intent(this, EasyService.class));
		finish();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode)
		{
			case 1:
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					startEasyService(null);
				}
				else
				{
					startEasyService(null);
					Toast.makeText(this, "Permission deny", Toast.LENGTH_SHORT).show();
				}
			break;
			default:
				break;
		}
	}


	public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

	@TargetApi(Build.VERSION_CODES.M)
	public void requestDrawOverLays() {
		if (!Settings.canDrawOverlays(SoEasy.this)) {
			Toast.makeText(this, "can not DrawOverlays", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + SoEasy.this.getPackageName()));
			startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
		} else {
			// Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
			if (!Settings.canDrawOverlays(this)) {
				// SYSTEM_ALERT_WINDOW permission not granted...
				Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
				// Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
			}
		}
	}
}
