package com.lcmf.xll.recyclerviewdemo.Tuling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class TextAdapter extends BaseAdapter {
	List<ListData> lists;
	private Context mContext;
	private RelativeLayout relativeLayout;

	public TextAdapter(List<ListData> lists, Context mContext) {
		super();
		this.lists = lists;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return lists == null ? 0 : lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if(lists.get(position).getFlag() == ListData.RECEIVE)
		{
			relativeLayout = (RelativeLayout)inflater.inflate(R.layout.tuling_left, null);
		}
		if(lists.get(position).getFlag() == ListData.SEND)
		{
			relativeLayout = (RelativeLayout)inflater.inflate(R.layout.tuling_right, null);
		}

		TextView tv = (TextView)relativeLayout.findViewById(R.id.tv);
		TextView time = (TextView)relativeLayout.findViewById(R.id.id_time);
		tv.setText(lists.get(position).getContext());
		time.setText(lists.get(position).getTime());

		return relativeLayout;
	}
}
