package com.lcmf.xll.recyclerviewdemo.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class FragmentNestActivity extends FragmentActivity implements View.OnClickListener {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nest_fragment);

		//初始化响应事件
		initView();
	}

	public void initView()
	{
		findViewById(R.id.btnModule1).setOnClickListener(this);
		findViewById(R.id.btnModule2).setOnClickListener(this);
		findViewById(R.id.btnModule3).setOnClickListener(this);
		findViewById(R.id.btnModule1).performClick();
	}


	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btnModule1:
				addFragmentToTask(FragmentParent.newInstance(0));
				break;
			case R.id.btnModule2:
				break;
			case R.id.btnModule3:
				break;
		}
	}

	public void addFragmentToTask(Fragment fragment){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, fragment);
		ft.commit();
	}

	/*嵌套Fragment*/
	public final static class FragmentParent extends Fragment{
		public static final FragmentParent newInstance(int position){
			FragmentParent f = new FragmentParent();
			Bundle args = new Bundle(2);
			args.putInt("position", position);
			f.setArguments(args);
			return f;
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			View convertView = inflater.inflate(R.layout.viewpager_fragment, container, false);
			ViewPager pager = (ViewPager)convertView.findViewById(R.id.pager);

			final int parent_position = getArguments().getInt("position");
			pager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
				@Override
				public Fragment getItem(final int position) {
					return new RelaceFragment(position);
				}

				@Override
				public int getCount() {
					return 3;
				}
				@Override
				public CharSequence getPageTitle(int position) {
					return "Page " + parent_position + " - " + position;
				}

			});
			return convertView;
		}
	}
}
