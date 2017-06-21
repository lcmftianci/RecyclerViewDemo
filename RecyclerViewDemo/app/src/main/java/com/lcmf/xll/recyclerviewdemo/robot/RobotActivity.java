package com.lcmf.xll.recyclerviewdemo.robot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class RobotActivity extends Activity {

	private ListView mMsgs;
	private ChatAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private EditText mInputMsg;
	private Button mSendMsg;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mDatas.add(fromMessge);
			mAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mDatas.size()-1);
		};

	};


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chan_dlg);

		InitViews();
		InitDatas();
		InitListener();
	}

	public void InitViews()
	{
		mMsgs = (ListView) findViewById(R.id.id_list_msg);
		mInputMsg = (EditText)findViewById(R.id.id_edit_msg);
		mSendMsg = (Button)findViewById(R.id.id_send_msg);
	}

	public void InitDatas() {
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("你好，我是小蛋", ChatMessage.Type.INCOMING, new Date()));
		mDatas.add(new ChatMessage("你好", ChatMessage.Type.OUTCOMING, new Date()));
		mAdapter = new ChatAdapter(this, mDatas);

		mMsgs.setAdapter(mAdapter);
	}

	private void InitListener()
	{
		mSendMsg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				if(TextUtils.isEmpty(toMsg))
				{
					Toast.makeText(RobotActivity.this, "消息不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}

				//更新界面
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(ChatMessage.Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size()-1);

				mInputMsg.setText("");

				new Thread(){
					public void run()
					{
						ChatMessage fromMessage = HttpUtils.sendChatMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					};
				}.start();
			}
		});
	}
}
