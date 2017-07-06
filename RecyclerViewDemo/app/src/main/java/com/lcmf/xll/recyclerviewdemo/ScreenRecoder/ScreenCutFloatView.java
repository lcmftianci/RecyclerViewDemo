package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.lcmf.xll.recyclerviewdemo.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ScreenCutFloatView extends LinearLayout {
	/*更新小悬浮窗的位置*/
	private WindowManager mManager;
	/*记录悬浮窗的宽高*/
	private static int nWidth;
	private static int nHeight;
	/*获取LinearLayout的数据*/
	private View mView;
	/*记录手指在当前屏幕上的位置*/
	private float mXInScreen;
	private float mYInScreen;
	/*手指按下时在屏幕上的位置*/
	private float mXDownScreen;
	private float mYDownScreen;
	/*手指按下时小悬浮窗在View上的位置*/
	private float mXInView;
	private float mYInView;
	/*记录系统状态栏的高度*/
	private static int mStatusBarHeight;

	public ScreenCutFloatView(Context context) {
		super(context);
		mManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		mView = findViewById(R.id.id_screen_cut);
		nWidth = mView.getLayoutParams().width;
		nHeight = mView.getLayoutParams().height;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:/*点击屏幕时，需要获取的数据*/
				mXInView = event.getX();/*getX与getRawX区别，一个是获取控件View上位置，一个是屏幕上的位置，并且getX永远不可能超过View的宽度，Y同样*/
				mYInView = event.getY();
				mXInScreen = event.getRawX();
				mYInScreen = event.getRawY() - getStatusBarHeight();
				mXDownScreen = event.getRawX();
				mYDownScreen = event.getRawY() - getStatusBarHeight();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
		}
		return true;
	}

	/*获取状态栏高度*/
	public int getStatusBarHeight() {
		if(mStatusBarHeight == 0){
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				mStatusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mStatusBarHeight;
	}
}
