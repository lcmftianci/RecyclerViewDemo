package com.lcmf.xll.recyclerviewdemo.DefView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * 自定义View控件
 * Created by Administrator on 2017/6/25 0025.
 */

public class TopBar extends RelativeLayout {

	//成员变量
	private Button leftBtn, rightBtn;
	private TextView tvTitle;
	private int leftTexColor;
	private Drawable leftBackground;
	private String leftText;
	private int rightTexColor;
	private Drawable rightBackground;
	private String rightText;
	private float titleTextSize;
	private int titleTextColor;
	private String titleText;

	private LayoutParams leftParams, rightParams, titleParams;

	private topbarOnClickListener listener;

	//新建接口
	public interface topbarOnClickListener{
		public void leftClick();
		public void rightClick();
	}

	public void setOnTopbarClickListener(topbarOnClickListener listener){
		this.listener = listener;
	}


	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
		leftTexColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
		leftBackground = ta.getDrawable(R.styleable.TopBar_leftTextBackground);
		leftText = ta.getString(R.styleable.TopBar_leftText);

		rightTexColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
		rightBackground = ta.getDrawable(R.styleable.TopBar_rightTextBackground);
		rightText = ta.getString(R.styleable.TopBar_rightText);

		titleText = ta.getString(R.styleable.TopBar_title);
		titleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor, 0);
		titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 0);

		ta.recycle();//回收

		leftBtn = new Button(context);
		rightBtn =  new Button(context);
		tvTitle = new TextView(context);

		leftBtn.setText(leftText);
		leftBtn.setBackground(leftBackground);
		leftBtn.setTextColor(leftTexColor);

		rightBtn.setText(rightText);
		rightBtn.setBackground(rightBackground);
		rightBtn.setTextColor(rightTexColor);

		tvTitle.setText(titleText);
		tvTitle.setTextColor(titleTextColor);
		tvTitle.setTextSize(titleTextSize);
		tvTitle.setGravity(Gravity.CENTER);

		setBackgroundColor(0xfff59563);

		leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		addView(leftBtn, leftParams);

		rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		addView(rightBtn, rightParams);

		titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
		addView(tvTitle, titleParams);

		//点击相应事件
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.leftClick();
			}
		});

		rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.rightClick();
			}
		});
	}

	//按键隐藏
	public void setLeftBtnVisible(boolean flag)
	{
		if(flag)
		{
			leftBtn.setVisibility(View.VISIBLE);
		}
		else
		{
			leftBtn.setVisibility(View.GONE);
		}
	}
}
