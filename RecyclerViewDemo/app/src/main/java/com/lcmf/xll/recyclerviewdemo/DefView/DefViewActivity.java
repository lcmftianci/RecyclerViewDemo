package com.lcmf.xll.recyclerviewdemo.DefView;

import android.app.Activity;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/6/25 0025.
 */

public class DefViewActivity extends Activity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.def_view_activity);
		TopBar topBar = (TopBar)findViewById(R.id.id_topbar);
		topBar.setOnTopbarClickListener(new TopBar.topbarOnClickListener(){
			@Override
			public void leftClick() {
				Toast.makeText(DefViewActivity.this, "this", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void rightClick() {
				Toast.makeText(DefViewActivity.this, "that", Toast.LENGTH_SHORT).show();
			}
		});

		topBar.setLeftBtnVisible(false);
	}
}
