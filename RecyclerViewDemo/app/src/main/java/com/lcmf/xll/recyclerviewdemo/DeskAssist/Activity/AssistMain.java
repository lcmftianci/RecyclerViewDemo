package com.lcmf.xll.recyclerviewdemo.DeskAssist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lcmf.xll.recyclerviewdemo.DeskAssist.Service.AssistService;
import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class AssistMain extends Activity {
	private int getLayoutResID() {
		return R.layout.easy_btn_activity;
	}

	private Button mShowViewButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(getLayoutResID());
		initEvent();
	}

	private void initEvent() {
		initViews();
		setViews();
	}

	private void initViews() {
		mShowViewButton = (Button) findViewById(R.id.activity_main_show_touchview_button);
	}

	private void setViews() {
		mShowViewButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startAuxiliaryService(null);
			}
		});
	}

	public void startAuxiliaryService(View v) {
		startService(new Intent(this, AssistService.class));
		finish();
	}
}
