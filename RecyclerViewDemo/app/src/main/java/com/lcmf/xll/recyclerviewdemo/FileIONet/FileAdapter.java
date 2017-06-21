package com.lcmf.xll.recyclerviewdemo.FileIONet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
	public FileAdapter() {
	}


	@Override
	public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}


	@Override
	public void onBindViewHolder(FileAdapter.ViewHolder holder, int position) {

	}


	@Override
	public int getItemCount() {
		return 0;
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
}
