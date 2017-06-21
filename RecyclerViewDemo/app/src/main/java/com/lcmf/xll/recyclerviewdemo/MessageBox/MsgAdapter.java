package com.lcmf.xll.recyclerviewdemo.MessageBox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

	//1.variable
	private List<Msg> mMsgList;


	//2.static class
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		TextView leftMsg;
		TextView rightMsg;

		public ViewHolder(View itemView) {
			super(itemView);
			leftLayout = (LinearLayout)itemView.findViewById(R.id.left_layout);
			rightLayout = (LinearLayout)itemView.findViewById(R.id.right_layout);
			leftMsg = (TextView)itemView.findViewById(R.id.left_msg);
			rightMsg = (TextView)itemView.findViewById(R.id.right_msg);
		}
	}


	//3.constructor
	public MsgAdapter(List<Msg> msgList)
	{
		mMsgList = msgList;
	}


	//4.create viewholder
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
		return new ViewHolder(view);
	}

	//5.bindViewHolder
	@Override
	public void onBindViewHolder(MsgAdapter.ViewHolder holder, int position) {
		Msg msg = mMsgList.get(position);
		if(msg.getType() == Msg.TYPE_RECEIVED)
		{
			holder.leftLayout.setVisibility(View.VISIBLE);
			holder.rightLayout.setVisibility(View.GONE);
			holder.leftMsg.setText(msg.getContent());
			holder.leftLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_SHORT).show();
				}
			});
		}
		else if(msg.getType() == Msg.TYPE_SEND)
		{
			holder.leftLayout.setVisibility(View.GONE);//尴尬了
			holder.rightLayout.setVisibility(View.VISIBLE);
			holder.rightMsg.setText(msg.getContent());
			holder.rightLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "Hello Right", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	//6.get count
	@Override
	public int getItemCount() {
		return mMsgList.size();
	}
}
