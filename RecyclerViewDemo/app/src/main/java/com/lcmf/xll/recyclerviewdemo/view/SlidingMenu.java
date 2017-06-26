package com.lcmf.xll.recyclerviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.lcmf.xll.recyclerviewdemo.R;
import com.nineoldandroids.view.ViewHelper;

import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by Administrator on 2017/6/24 0024.
 */

public class SlidingMenu extends HorizontalScrollView {
	//定义变量
	private LinearLayout mLayout;
	private ViewGroup mMenu;
	private ViewGroup mContent;

	private int mScreenWidth;
	private int mMenuRightPadding = 50;
	private int mMenuWidth;

	private boolean once = false;

	private boolean IsOpen = false;


	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!once) {
			mLayout = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mLayout.getChildAt(0);
			mContent = (ViewGroup) mLayout.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;

			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public SlidingMenu(Context context) {
		this(context, null);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//获取自定义属性
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
		int n = a.getIndexCount();
		for(int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
				case R.styleable.SlidingMenu_rightPadding:
					mMenuRightPadding = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
				break;
			}
		}

		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

		//dp - > px
//		mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed)
			this.scrollTo(mMenuWidth, 0);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale =l*1.0f/mMenuWidth;//1~0
		//ViewHelper.setTranslationX(mMenu, mMenuWidth*scale);
		//抽屉式完成

		float rightScale = 0.7f + 0.3f*scale;
		float leftScale = 1.0f - scale*0.3f;
		float leftAlpha = 0.6f + 0.4f * (1 - scale);
		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale*0.8f);
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);

		//设置内容动画
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setPivotY(mContent, rightScale);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action)
		{
			case ACTION_UP:
				int scrollX = getScrollX();
				if(scrollX >= mMenuWidth/2)
				{
					this.smoothScrollTo(mMenuWidth, 0);
					IsOpen = false;
				}
				else
				{
					this.smoothScrollTo(0, 0);
					IsOpen = true;
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}

	public void onOPenMenu()
	{
		if(IsOpen)return;
		this.smoothScrollTo(0, 0);
		IsOpen = true;
	}

	public void onCloseMenu()
	{
		if(!IsOpen)return;
		this.smoothScrollTo(mMenuWidth, 0);
		IsOpen = false;
	}

	public void toggle()
	{
		if(IsOpen)
		{
			onCloseMenu();
		}else {
			onOPenMenu();
		}
	}
}
