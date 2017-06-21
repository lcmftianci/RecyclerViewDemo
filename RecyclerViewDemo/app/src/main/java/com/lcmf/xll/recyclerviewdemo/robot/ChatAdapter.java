package com.lcmf.xll.recyclerviewdemo.robot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class ChatAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;

	public ChatAdapter(Context context, List<ChatMessage> mDatas)
	{
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage chatMessage = mDatas.get(position);
		if(chatMessage.getType() == ChatMessage.Type.INCOMING)
		{
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatMessage = mDatas.get(position);
		ViewHolder  viewHolder = null;
		if(convertView == null)
		{
			if(getItemViewType(position) == 0)
			{
				convertView = mInflater.inflate(R.layout.chan_in_dlg, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_from_msg_date);
				viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_from_text_info);
			}
			else if(getItemViewType(position) == 1)
			{
				convertView = mInflater.inflate(R.layout.chan_out_dlg, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_to_msg_date);
				viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_to_text_info);
			}
			convertView.setTag(viewHolder);
		}else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		//设置数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(df.format(chatMessage.getDate()));
		viewHolder.mMsg.setText(chatMessage.getMsg());

		return convertView;
	}

	private final class ViewHolder
	{
		TextView mDate;
		TextView mMsg;
	}
}
