package com.lcmf.xll.recyclerviewdemo.Tuling;

/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class ListData {
	private String context;
	public static final int SEND = 1;
	public static final int RECEIVE = 2;
	private int flag;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}



	public ListData(String context, int flag, String time) {
		setContext(context);
		setFlag(flag);
		setTime(time);
	}


	public String getContext()
	{
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
