package com.lcmf.xll.recyclerviewdemo.MessageBox;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lcmf.xll.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class MsgActivity extends Activity {

	//1.variable
	private List<Msg> msgList = new ArrayList<>();
	private EditText inputText;
	private Button send;
	private RecyclerView msgRecyclerView;
	private MsgAdapter adapter;

	//2.init msg content
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_dlg);
		initMsg();

		inputText = (EditText)findViewById(R.id.input_text);
		send = (Button)findViewById(R.id.send);
		msgRecyclerView = (RecyclerView)findViewById(R.id.msg_recycler_view);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		msgRecyclerView.setLayoutManager(layoutManager);
		adapter = new MsgAdapter(msgList);
		msgRecyclerView.setAdapter(adapter);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if(!"".equals(content))
				{
					Msg msg = new Msg(content, Msg.TYPE_SEND);
					msgList.add(msg);
					adapter.notifyItemChanged(msgList.size() - 1);

					msgRecyclerView.scrollToPosition(msgList.size() - 1);
					inputText.setText("");
				}
			}
		});
	}

	//3.init msg
	private void initMsg()
	{
		Msg msg1 = new Msg("Hello gay", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hello who are you?", Msg.TYPE_SEND);
		msgList.add(msg2);
		Msg msg3 = new Msg("this is ni ba", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
	}
}
