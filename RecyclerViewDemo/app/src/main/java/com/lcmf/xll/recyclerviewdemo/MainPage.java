package com.lcmf.xll.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lcmf.xll.recyclerviewdemo.view.SlidingMenu;


/**
 * Created by Administrator on 2017/6/17 0017.
 * 一般的侧滑实现
 * ViewGroup Menu + Content
 * onTouchEvent
 * MOVE:ViewGroup的leftMargin
 * UP:根据现实的宽度，决定将其隐藏还是显示
 * 1、Scroller
 * 2/LeftMargin + Thread
 *
 * 新方法
 * 继承HorizontalViewGroup
 * 自定义ViewGroup
 */

public class MainPage extends Activity{

	private SlidingMenu mLeftMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		mLeftMenu = (SlidingMenu)findViewById(R.id.id_menu);
	}
	public void toggleMenu(View v)
	{
		mLeftMenu.toggle();
	}
}
