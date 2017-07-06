package com.lcmf.xll.recyclerviewdemo.Notification3D;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.audiofx.NoiseSuppressor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.EasyBtn.SoEasy;
import com.lcmf.xll.recyclerviewdemo.R;

import static com.lcmf.xll.recyclerviewdemo.Notification3D.NotificationUtils.isNotificationEnabled;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class NotificationActivity extends Activity {

	//definition variable
	public final static String ACTION_BTN = "com.lcmf.xll.recycleviedemo";
	public final static String INTENT_NAME = "btnid";
	public final static int INTENT_BTN_LOGIN = 1;
	NotificationBroadcastReceiver mReceiver;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("---V---", "启动NotificationActivity");
		setContentView(R.layout.notification_activity);
//		if(ContextCompat.checkSelfPermission(NotificationActivity.this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED){
//			ActivityCompat.requestPermissions(NotificationActivity.this, new String[]{Manifest.permission.SET_ALARM}, 1);
//		}
		if(!isNotificationEnabled(this))
		{
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
				requestPermission(1);
		}

//		requestDrawOverLays();

		Button btn = (Button)findViewById(R.id.btn_notification);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("---V---", "启动通知栏消息");
				notification();
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode)
		{
			case 1:
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					Toast.makeText(NotificationActivity.this, "允许程序访问通知栏", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(NotificationActivity.this, "申请权限失败", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			default:
				break;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregeisterReceiver();
	}

	@TargetApi(Build.VERSION_CODES.M)
	protected void requestPermission(int requestCode) {
		// TODO Auto-generated method stub
		// 6.0以上系统才可以判断权限
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
			// 进入设置系统应用权限界面
			Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
			Log.v("---V---", "申请打开通知权限");
			//Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + NotificationActivity.this.getPackageName()));
			Log.v("---V---", "打开成功SDK>0.x");
			startActivity(intent);
			Log.v("---V---", "启动意图");
			return;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
			// 进入设置系统应用权限界面
			Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
			Log.v("---V---", "申请打开通知权限");
			//Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + NotificationActivity.this.getPackageName()));
			Log.v("---V---", "打开成功SDK>5.x");
			startActivity(intent);
			Log.v("---V---", "启动意图5.x");
			return;
		}
		return;
	}

	private void notification() {
		Log.v("---V---", "取消注册消息");
		unregeisterReceiver();
		Log.v("---V---", "取消注册消息成功");
		intiReceiver();
		Log.v("---V---", "初始化注册消息");

		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_main);
		remoteViews.setTextViewText(R.id.tv_up, "首都机场精品无线");
		remoteViews.setTextViewText(R.id.tv_down, "已免费接入");
		Log.v("---V---", "通知栏创建成功");

		Intent intent = new Intent(ACTION_BTN);
		intent.putExtra(INTENT_NAME, INTENT_BTN_LOGIN);
		PendingIntent intentpi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.btn_login, intentpi);
		Log.v("---V---", "创建按钮意图");

		Intent intent2 = new Intent();
		intent2.setClass(this, NotificationActivity.class);
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent intentContent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		Log.v("---V---", "创建界面更新意图");

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		builder.setOngoing(false);
		builder.setAutoCancel(false);
		builder.setContent(remoteViews);
		builder.setTicker("正在使用首都机场无线");
		builder.setSmallIcon(R.drawable.id_airport);

		Notification notification = builder.build();
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_NO_CLEAR;
		notification.contentIntent = intentContent;

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
		Log.v("---V---", "通知栏界面显示成功");
	}

	private void intiReceiver() {
		mReceiver = new NotificationBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BTN);
		getApplicationContext().registerReceiver(mReceiver, intentFilter);
	}

	private void unregeisterReceiver() {
		if (mReceiver != null) {
			getApplicationContext().unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	class NotificationBroadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(ACTION_BTN)){
				int btn_id = intent.getIntExtra(INTENT_NAME, 0);
				switch (btn_id){
					case INTENT_BTN_LOGIN:
						Toast.makeText(NotificationActivity.this, "通知栏登录", Toast.LENGTH_SHORT).show();
						unregeisterReceiver();
						NotificationManager notificationManager = (NotificationManager) NotificationActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
						notificationManager.cancel(0);
						break;
				}
			}
		}
	}

//	打开各种系统设置界面
//	Intent intent = new Intent("/");
//	ComponentName cm = new ComponentName("com.adnroid.settings","com.adnroid.settings.WirelsssSettings");
//	intent.setComponent(cm);
//	intent.setAction("android.intent.action.VIEW");
//	activity.startActivityForResult(intent, 0);

	public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

	@TargetApi(Build.VERSION_CODES.M)
	public void requestDrawOverLays() {
		if (!Settings.canDrawOverlays(NotificationActivity.this)) {
			Toast.makeText(this, "can not DrawOverlays", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + NotificationActivity.this.getPackageName()));
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
				Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
