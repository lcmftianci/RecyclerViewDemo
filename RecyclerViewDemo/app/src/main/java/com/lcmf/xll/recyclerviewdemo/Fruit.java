package com.lcmf.xll.recyclerviewdemo;

/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class Fruit {
	private String mName;

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public int getmImageId() {
		return mImageId;
	}

	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}

	private int mImageId;

	public Fruit(String mName, int mImageId) {
		this.mName = mName;
		this.mImageId = mImageId;
	}

}
