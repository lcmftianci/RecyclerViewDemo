package com.lcmf.xll.recyclerviewdemo.fragment.GLFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class NewsFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.gl_news_frag, container,
				false);
		return newsLayout;
	}
}
