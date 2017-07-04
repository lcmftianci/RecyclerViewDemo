package com.lcmf.xll.recyclerviewdemo.fragment.GLFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class GLFragmentActivity extends Activity implements View.OnClickListener{

	/*新建一大堆成员变量*/
	private MessageFragment msgFrag;
	private NewsFragment newsFrag;
	private ContactsFragment conFrag;
	private SettingFragment setFrag;

	private View msgLayout;
	private View conLayout;
	private View newsLayout;
	private View setLayout;

	private ImageView msgImg;
	private ImageView conImg;
	private ImageView newsImg;
	private ImageView setImg;

	private TextView newsText;
	private TextView conText;
	private TextView setText;
	private TextView msgText;

	private FragmentManager fragmentManager;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gl_fragment_activity);
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.message_layout:
				// 当点击了消息tab时，选中第1个tab
				setTabSelection(0);
				break;
			case R.id.contacts_layout:
				// 当点击了联系人tab时，选中第2个tab
				setTabSelection(1);
				break;
			case R.id.news_layout:
				// 当点击了动态tab时，选中第3个tab
				setTabSelection(2);
				break;
			case R.id.setting_layout:
				// 当点击了设置tab时，选中第4个tab
				setTabSelection(3);
				break;
			default:
				break;
		}
	}

	/*初始化界面*/
	private void initViews(){
		msgLayout = findViewById(R.id.message_layout);
		conLayout = findViewById(R.id.contacts_layout);
		newsLayout = findViewById(R.id.news_layout);
		setLayout = findViewById(R.id.setting_layout);
		msgImg = (ImageView)findViewById(R.id.message_image);
		conImg = (ImageView)findViewById(R.id.contacts_image);
		newsImg = (ImageView)findViewById(R.id.news_image);
		setImg = (ImageView)findViewById(R.id.setting_image);

		msgText = (TextView)findViewById(R.id.message_text);
		conText = (TextView)findViewById(R.id.contacts_text);
		setText = (TextView)findViewById(R.id.setting_text);
		newsText = (TextView)findViewById(R.id.news_text);

		msgLayout.setOnClickListener(this);
		conLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		setLayout.setOnClickListener(this);
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		msgImg.setImageResource(R.drawable.me);
		msgText.setTextColor(Color.parseColor("#82858b"));
		conImg.setImageResource(R.drawable.me);
		conText.setTextColor(Color.parseColor("#82858b"));
		newsImg.setImageResource(R.drawable.me);
		newsText.setTextColor(Color.parseColor("#82858b"));
		setImg.setImageResource(R.drawable.me);
		setText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (msgFrag != null) {
			transaction.hide(msgFrag);
		}
		if (conFrag != null) {
			transaction.hide(conFrag);
		}
		if (newsFrag != null) {
			transaction.hide(newsFrag);
		}
		if (setFrag != null) {
			transaction.hide(setFrag);
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 *
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				// 当点击了消息tab时，改变控件的图片和文字颜色
				msgImg.setImageResource(R.drawable.me);
				msgText.setTextColor(Color.WHITE);
				if (msgFrag == null) {
					// 如果MessageFragment为空，则创建一个并添加到界面上
					msgFrag = new MessageFragment();
					transaction.add(R.id.content, msgFrag);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(msgFrag);
				}
				break;
			case 1:
				// 当点击了联系人tab时，改变控件的图片和文字颜色
				conImg.setImageResource(R.drawable.me);
				conText.setTextColor(Color.WHITE);
				if (conFrag == null) {
					// 如果ContactsFragment为空，则创建一个并添加到界面上
					conFrag = new ContactsFragment();
					transaction.add(R.id.content, conFrag);
				} else {
					// 如果ContactsFragment不为空，则直接将它显示出来
					transaction.show(conFrag);
				}
				break;
			case 2:
				// 当点击了动态tab时，改变控件的图片和文字颜色
				newsImg.setImageResource(R.drawable.me);
				newsText.setTextColor(Color.WHITE);
				if (newsFrag == null) {
					// 如果NewsFragment为空，则创建一个并添加到界面上
					newsFrag = new NewsFragment();
					transaction.add(R.id.content, newsFrag);
				} else {
					// 如果NewsFragment不为空，则直接将它显示出来
					transaction.show(newsFrag);
				}
				break;
			case 3:
			default:
				// 当点击了设置tab时，改变控件的图片和文字颜色
				setImg.setImageResource(R.drawable.me);
				setText.setTextColor(Color.WHITE);
				if (setFrag == null) {
					// 如果SettingFragment为空，则创建一个并添加到界面上
					setFrag = new SettingFragment();
					transaction.add(R.id.content, setFrag);
				} else {
					// 如果SettingFragment不为空，则直接将它显示出来
					transaction.show(setFrag);
				}
				break;
		}
		transaction.commit();
	}

}
