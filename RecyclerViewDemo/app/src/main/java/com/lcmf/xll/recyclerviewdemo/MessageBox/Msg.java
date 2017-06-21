package com.lcmf.xll.recyclerviewdemo.MessageBox;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class Msg {
	//1.flag
	public static final int TYPE_RECEIVED = 0;
	public static final int TYPE_SEND = 1;

	//2.content
	private String content;
	private int type;

	//3.constructer
	public Msg(String content, int type) {
		this.content = content;
		this.type = type;
	}

	//4.getset


	public static int getTypeReceived() {
		return TYPE_RECEIVED;
	}

	public static int getTypeSend() {
		return TYPE_SEND;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
