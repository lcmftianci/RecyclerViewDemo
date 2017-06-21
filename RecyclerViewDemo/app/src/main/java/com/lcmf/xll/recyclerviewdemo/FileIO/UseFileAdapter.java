package com.lcmf.xll.recyclerviewdemo.FileIO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcmf.xll.recyclerviewdemo.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class UseFileAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Bitmap directory, Mp3file, VedioFile, TxtFile, PdfFile, UnKnowFile;

	//file name
	private ArrayList<String> mNames = null;
	//file path
	private ArrayList<String> mPaths = null;

	//constructor
	public UseFileAdapter(Context context, ArrayList<String> name, ArrayList<String> path)
	{
		mNames = name;
		mPaths = path;
		directory = BitmapFactory.decodeResource(context.getResources(), R.drawable.foldericon);
		UnKnowFile = BitmapFactory.decodeResource(context.getResources(), R.drawable.fileicon);

		//shrunk pic
		directory = small(directory, 0.16f);
		UnKnowFile = small(UnKnowFile, 0.1f);
		inflater = LayoutInflater.from(context);
	}
	private Bitmap small(Bitmap map,float num){
		Matrix matrix = new Matrix();
		matrix.postScale(num, num);
		return Bitmap.createBitmap(map,0,0,map.getWidth(),map.getHeight(),matrix,true);
	}


	@Override
	public int getCount() {
		return mNames.size();
	}


	@Override
	public Object getItem(int position) {
		return mNames.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
		if(null == convertView)
		{
			convertView = inflater.inflate(R.layout.file_item, null);
			holder = new ViewHolder();
			holder.text = (TextView)convertView.findViewById(R.id.file_text);
			holder.image = (ImageView)convertView.findViewById(R.id.file_imageView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		//set file pic
		File tempfile = new File(mPaths.get(position).toString());
		if(mNames.get(position).equals("@1"))
		{
			holder.text.setText("/");
			holder.image.setImageBitmap(directory);
		}
		else if(mNames.get(position).equals("@2"))
		{
			holder.text.setText("..");
			holder.image.setImageBitmap(directory);
		}
		else
		{
			holder.text.setText(tempfile.getName());
			if(tempfile.isDirectory())
			{
				holder.image.setImageBitmap(directory);
			}
			else if(tempfile.isFile())
			{
				holder.image.setImageBitmap(UnKnowFile);
			}
			else
			{
				System.out.println(tempfile.getName());
			}
		}
			return convertView;
	}

	//viewholder
	private class ViewHolder
	{
		private TextView text;
		private ImageView image;
	}
}
