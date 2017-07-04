package com.lcmf.xll.recyclerviewdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class RelaceFragment extends Fragment {
	private int mPos;

	public RelaceFragment() {
	}

	public RelaceFragment(int pos) {
		this.mPos = pos;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
		textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(30);
		textView.setTextColor(Color.BLACK);
		textView.setText("Page " + mPos);
		return textView;
	}
}
