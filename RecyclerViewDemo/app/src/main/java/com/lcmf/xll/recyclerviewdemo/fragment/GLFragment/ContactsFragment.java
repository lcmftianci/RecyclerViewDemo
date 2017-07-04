package com.lcmf.xll.recyclerviewdemo.fragment.GLFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class ContactsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.gl_cont_frag,
				container, false);
		return contactsLayout;
	}

}
