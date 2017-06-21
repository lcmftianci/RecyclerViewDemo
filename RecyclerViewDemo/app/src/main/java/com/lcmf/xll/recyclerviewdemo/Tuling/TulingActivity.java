package com.lcmf.xll.recyclerviewdemo.Tuling;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lcmf.xll.recyclerviewdemo.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class TulingActivity extends Activity implements HttpGetDataListener,View.OnClickListener {

	private HttpData mHttpData;
	private List<ListData> lists;
	private ListView lv;
	private Button btn;
	private EditText edit;
	private String msg;
	private TextAdapter adapter;
	private String welcome_array[];
	private double currentTime, oldTime = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuling_activity);
		initView();
	}

	private String getRandomWelcomeTips()
	{
		String welcome_tips = null;
		welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
		int index = (int)((Math.random())*(welcome_array.length - 1));
		welcome_tips = welcome_array[index];
		return welcome_tips;
	}

	private void initView()
	{
		lv = (ListView)findViewById(R.id.lv);
		btn = (Button)findViewById(R.id.send_btn);
		edit = (EditText)findViewById(R.id.send_edit);
		lists = new ArrayList<ListData>();
		btn.setOnClickListener(this);
		adapter = new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		ListData listData;
		listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVE, getTime());
		lists.add(listData);
	}

	@Override
	public void GetDataUrl(String data) {
		//System.out.print(data);
		parseText(data);
	}

	public void parseText(String str){
		try
		{
			JSONObject jb = new JSONObject(str);
//			System.out.println(jb.getString("code"));
//			System.out.println(jb.getString("text"));
			ListData listData;
			listData = new ListData(jb.getString("text"), ListData.RECEIVE, getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		getTime();
		msg = edit.getText().toString();
		edit.setText("");
		String dropk = msg.replace(" ", "");
		String droph = dropk.replace("\n", "");
		ListData listData;
		listData = new ListData(msg, ListData.SEND, getTime());
		lists.add(listData);

		if(lists.size() > 30)
		{
			for(int i = 0; i < lists.size(); i++)
			{
				lists.remove(i);
			}
		}

		adapter.notifyDataSetChanged();
		mHttpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=20fe5e4db9f94a25be03592bed8a5262&info=" + droph, this).execute();
	}

	public String getTime(){
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date();
		String str = format.format(curDate);
		if(currentTime - oldTime >= 5*60*1000)
		{
			oldTime = currentTime;
			return str;

		}else
		{
			return "";
		}
	}
}
