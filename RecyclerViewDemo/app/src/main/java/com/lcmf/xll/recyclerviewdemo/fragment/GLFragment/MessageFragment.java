package com.lcmf.xll.recyclerviewdemo.fragment.GLFragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class MessageFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.gl_msg_frag, container, false);
		return messageLayout;
	}
}
