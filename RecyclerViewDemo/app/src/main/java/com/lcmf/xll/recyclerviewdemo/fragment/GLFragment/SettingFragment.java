package com.lcmf.xll.recyclerviewdemo.fragment.GLFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class SettingFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View settingView = inflater.inflate(R.layout.gl_setting_frag, container, false);
		return settingView;
	}
}
