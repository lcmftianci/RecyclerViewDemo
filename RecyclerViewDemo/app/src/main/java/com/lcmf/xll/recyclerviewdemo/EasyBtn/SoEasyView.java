package com.lcmf.xll.recyclerviewdemo.EasyBtn;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.lcmf.xll.recyclerviewdemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class SoEasyView extends View {

	/*声明成员变量*/
	private Context mContext;
	private WindowManager mWManager;
	private WindowManager.LayoutParams mWMParams;
	private ServiceListener mServiceListener;
	private int mWX;
	private PopupWindow mPopuWin;
	private int mWY;
	private int mTag;
	private Toast mToast;
	private Timer mTimer = null;
	private TimerTask mTask = null;
	private View mTouchView;
	private View mSettingTable;
	private ImageView mIconImageView = null;
	private int mOldOffsetX;
	private int mOldOffsetY;


	//构造函数
	public SoEasyView(Context context, ServiceListener listener) {
		super(context);
		mContext = context;
		mServiceListener = listener;
	}

	public interface ServiceListener{
		public void OnCloseService(boolean isClose);
	}

	public void initSoEasyTouchEvent() {
		initSoEasyViewEvent();
		initSettingTableView();
	}

	/*初始化TouchView*/
	private void initSoEasyViewEvent(){
		/*获取屏幕宽高*/
		mWManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		mWX = mWManager.getDefaultDisplay().getWidth()/2 - 25;
		mWY = mWManager.getDefaultDisplay().getHeight()/2 - 44;
//		DisplayMetrics outMetrics = new DisplayMetrics();
//		mWManager.getDefaultDisplay().getMetrics(outMetrics);
//		mWX = outMetrics.widthPixels;
//		mWY = outMetrics.heightPixels;

		//获取桌面工具资源
		mTouchView = LayoutInflater.from(mContext).inflate(R.layout.easy_btn_touch_view, null);

		//获取桌面工具图标
		mIconImageView = (ImageView)mTouchView.findViewById(R.id.easy_touch_view_imageview);
		mTouchView.setBackgroundColor(Color.TRANSPARENT);

		//设置响应事件
		mTouchView.setOnTouchListener(mTouchListener);
		WindowManager wm = mWManager;
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		mWMParams = wmParams;
		//2002表示系统级窗口
		wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
		wmParams.flags = 40;
		wmParams.width = 100;
		wmParams.height = 100;
		wmParams.format = -3; //透明
		wm.addView(mTouchView, wmParams);
	}

	private void initSettingTableView(){
		mSettingTable = LayoutInflater.from(mContext).inflate(R.layout.easy_btn_setting, null);
		Button commonUseButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_common_use_button);
		Button screenLockButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_screen_lock_button);
		Button notificationButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_notification_button);

		Button phoneButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_phone_button);
		Button pageButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_page_button);
		Button cameraButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_camera_button);

		Button backButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_back_button);
		Button homeButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_home_button);
		Button exitTouchButton = (Button) mSettingTable.findViewById(R.id.show_setting_table_item_exit_touch_button);

		commonUseButton.setOnClickListener(mClickListener);
		screenLockButton.setOnClickListener(mClickListener);
		notificationButton.setOnClickListener(mClickListener);

		phoneButton.setOnClickListener(mClickListener);
		pageButton.setOnClickListener(mClickListener);
		cameraButton.setOnClickListener(mClickListener);

		backButton.setOnClickListener(mClickListener);
		homeButton.setOnClickListener(mClickListener);
		exitTouchButton.setOnClickListener(mClickListener);
	}

	private OnClickListener mClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.show_setting_table_item_common_use_button:
					hideSettingTable("常用");
					break;
				case R.id.show_setting_table_item_screen_lock_button:
					hideSettingTable("锁屏");
					break;
				case R.id.show_setting_table_item_notification_button:
					hideSettingTable("通知");
					break;

				case R.id.show_setting_table_item_phone_button:
					hideSettingTable("电话");
					break;
				case R.id.show_setting_table_item_page_button:
					hideSettingTable("1");
					break;
				case R.id.show_setting_table_item_camera_button:
					hideSettingTable("相机");
					break;

				case R.id.show_setting_table_item_back_button:
					hideSettingTable("返回");
					break;
				case R.id.show_setting_table_item_home_button:
					hideSettingTable("主页");
					break;
				case R.id.show_setting_table_item_exit_touch_button:
					quitTouchView();
					break;
			}
		}
	};

	private void quitTouchView() {
		hideSettingTable("退出");

		mWManager.removeView(mTouchView);
		mServiceListener.OnCloseService(true);

		clearTimerThead();
	}

	private OnTouchListener mTouchListener = new OnTouchListener() {
		float lastX, lastY;
		int paramX, paramY;

		public boolean onTouch(View v, MotionEvent event) {
			final int action = event.getAction();

			float x = event.getRawX();
			float y = event.getRawY();

			if (mTag == 0) {
				mOldOffsetX = mWMParams.x; // 偏移量
				mOldOffsetY = mWMParams.y; // 偏移量
			}

			switch (action) {
				case MotionEvent.ACTION_DOWN:
					motionActionDownEvent(x, y);
					break;

				case MotionEvent.ACTION_MOVE:
					motionActionMoveEvent(x, y);
					break;

				case MotionEvent.ACTION_UP:
					motionActionUpEvent(x, y);
					break;

				default:
					break;
			}

			return true;
		}

		private void motionActionDownEvent(float x, float y) {
			lastX = x;
			lastY = y;
			paramX = mWMParams.x;
			paramY = mWMParams.y;
		}

		private void motionActionMoveEvent(float x, float y) {
			int dx = (int) (x - lastX);
			int dy = (int) (y - lastY);
			mWMParams.x = paramX + dx;
			mWMParams.y = paramY + dy;
			mTag = 1;

			// 更新悬浮窗位置
			mWManager.updateViewLayout(mTouchView, mWMParams);
		}

		private void motionActionUpEvent(float x, float y) {
			int newOffsetX = mWMParams.x;
			int newOffsetY = mWMParams.y;
			if (mOldOffsetX == newOffsetX && mOldOffsetY == newOffsetY) {
				mPopuWin = new PopupWindow(mSettingTable, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mPopuWin.setTouchInterceptor(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
							hideSettingTable();
							return true;
						}
						return false;
					}
				});

				mPopuWin.setBackgroundDrawable(new BitmapDrawable());
				mPopuWin.setTouchable(true);
				mPopuWin.setFocusable(true);
				mPopuWin.setOutsideTouchable(true);
				mPopuWin.setContentView(mSettingTable);

				if (Math.abs(mOldOffsetX) > mWX) {
					if (mOldOffsetX > 0) {
						mOldOffsetX = mWX;
					} else {
						mOldOffsetX = -mWX;
					}
				}

				if (Math.abs(mOldOffsetY) > mWY) {
					if (mOldOffsetY > 0) {
						mOldOffsetY = mWY;
					} else {
						mOldOffsetY = -mWY;
					}
				}

				mPopuWin.setAnimationStyle(R.style.AnimationPreview);
				mPopuWin.setFocusable(true);
				mPopuWin.update();
				mPopuWin.showAtLocation(mTouchView, Gravity.CENTER, -mOldOffsetX, -mOldOffsetY);

				// TODO
				mIconImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

				catchSettingTableDismiss();
			} else {
				mTag = 0;
			}
		}
	};

	private void catchSettingTableDismiss() {
		mTimer = new Timer();
		mTask = new TimerTask() {

			@Override
			public void run() {
				if (mPopuWin == null || !mPopuWin.isShowing()) {
					handler.sendEmptyMessage(0x0);
				}
			}
		};

		mTimer.schedule(mTask, 0, 100);
	}

	private void clearTimerThead() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mIconImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.touch_ic));
		};
	};

	public void showToast(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	private void hideSettingTable(String content) {
		hideSettingTable();
		showToast(mContext, content);
	}

	private void hideSettingTable() {
		if (null != mPopuWin) {
			mPopuWin.dismiss();
		}
	}

}
