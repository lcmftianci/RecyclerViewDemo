package com.lcmf.xll.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class FruitAdaper extends RecyclerView.Adapter<FruitAdaper.ViewHolder> {

	//creat context
	private Context mContext;
	//create List
	private List<Fruit> mFruitList;

	//constructor
	public FruitAdaper(List<Fruit> mFruitList) {
		this.mFruitList = mFruitList;
	}

	//create static class viewholder
	static class ViewHolder extends RecyclerView.ViewHolder{
		ImageView fruitImage;
		TextView fruitName;

		public ViewHolder(View itemView) {
			super(itemView);
			fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
			fruitName = (TextView)itemView.findViewById(R.id.fruit_name);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(mContext == null)
			mContext = parent.getContext();
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Fruit fruit = mFruitList.get(position);
//		holder.fruitImage.setImageResource(fruit.getmImageId());
		holder.fruitName.setText(fruit.getmName());
		Glide.with(mContext).load(fruit.getmImageId()).into(holder.fruitImage);
	}

	@Override
	public int getItemCount() {
		return mFruitList.size();
	}
}
