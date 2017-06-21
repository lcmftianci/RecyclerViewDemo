package com.lcmf.xll.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lcmf.xll.recyclerviewdemo.FileIO.FileDlg;
import com.lcmf.xll.recyclerviewdemo.FileSave.LitepalSave;
import com.lcmf.xll.recyclerviewdemo.MessageBox.MsgActivity;
import com.lcmf.xll.recyclerviewdemo.Tuling.TulingActivity;
import com.lcmf.xll.recyclerviewdemo.robot.RobotActivity;
import com.lcmf.xll.recyclerviewdemo.xunfei.XunFeiActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class FruitAdaper extends RecyclerView.Adapter<FruitAdaper.ViewHolder> {

	//1.indecate variable
	//creat context
	private Context mContext;
	//create List
	private List<Fruit> mFruitList;

	//2.constructor
	public FruitAdaper(List<Fruit> mFruitList) {
		this.mFruitList = mFruitList;
	}

	//3.create static class viewholder
	static class ViewHolder extends RecyclerView.ViewHolder{
		View fruitView;
		ImageView fruitImage;
		TextView fruitName;

		public ViewHolder(View itemView) {
			super(itemView);
			fruitView = itemView;
			fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
			fruitName = (TextView)itemView.findViewById(R.id.fruit_name);
		}
	}

	//4.create function get viewholder
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(mContext == null)
			mContext = parent.getContext();
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);

		final ViewHolder holder = new ViewHolder(view);
		 holder.fruitView.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 int pos = holder.getAdapterPosition();
				 Fruit fruit = mFruitList.get(pos);
				 Toast.makeText(v.getContext(), "准备启动程序" + fruit.getmName(), Toast.LENGTH_SHORT).show();
				 if(Integer.compare(pos, 1) == 0)
				 {
					 Intent intent = new Intent(v.getContext(), FileDlg.class);
					 v.getContext().startActivity(intent);
				 }
				 else if(Integer.compare(pos, 2) == 0)
				 {
					 Intent intent = new Intent(v.getContext(), MsgActivity.class);
					 v.getContext().startActivity(intent);
				 }
				 else if(Integer.compare(pos, 3) == 0)
				 {
					 Intent intent = new Intent(v.getContext(), RobotActivity.class);
					 v.getContext().startActivity(intent);
				 }
				 else if(Integer.compare(pos, 4) == 0)
				 {
					 Intent intent = new Intent(v.getContext(), TulingActivity.class);
					 v.getContext().startActivity(intent);
				 }
				 else if(Integer.compare(pos, 5) == 0)
				 {
					 Intent intent = new Intent(v.getContext(), XunFeiActivity.class);
					 v.getContext().startActivity(intent);
				 }
				 else if(Integer.compare(pos, 6) == 0) {
					 Intent intent = new Intent(v.getContext(), LitepalSave.class);
					 v.getContext().startActivity(intent);
				 }
				 else
				 {
					 Toast.makeText(v.getContext(), "启动程序失败！" + fruit.getmName(), Toast.LENGTH_SHORT).show();
				 }
			 }
		 });
		holder.fruitName.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				int pos = holder.getAdapterPosition();
				Fruit fruit = mFruitList.get(pos);
				Toast.makeText(v.getContext(), "名称响应" + fruit.getmName(), Toast.LENGTH_SHORT).show();
			}
		});
		return holder;
	}

	//5.bind viewholder
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Fruit fruit = mFruitList.get(position);
//		holder.fruitImage.setImageResource(fruit.getmImageId());
		holder.fruitName.setText(fruit.getmName());
		Glide.with(mContext).load(fruit.getmImageId()).into(holder.fruitImage);
	}

	//6.get count
	@Override
	public int getItemCount() {
		return mFruitList.size();
	}
}
