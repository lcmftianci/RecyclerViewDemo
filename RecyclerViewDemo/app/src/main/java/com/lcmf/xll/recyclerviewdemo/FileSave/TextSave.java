package com.lcmf.xll.recyclerviewdemo.FileSave;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class TextSave extends Activity{
	private EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.textsave);

		edit = (EditText)findViewById(R.id.id_save);

		//回复上次保存的数据
		String strInput = load();
		if(!TextUtils.isEmpty(strInput))
		{
			edit.setText(strInput);
			edit.setSelection(strInput.length());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		String inputText = edit.getText().toString();
		save(inputText);
	}

	//文件保存
	public void save(String strData){
//		String data = "Data To Save";
		FileOutputStream out = null;
		BufferedWriter writer = null;

		try{
			out = openFileOutput("a.txt", Context.MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(strData);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(writer != null)
					writer.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}


	//文件加载
	public String load(){
		FileInputStream in = null;
		BufferedReader reader = null;
		StringBuffer content = new StringBuffer();
		try {
			in = openFileInput("a.txt");
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			try{
				while((line = reader.readLine()) != null)   {
					content.append(line);
				}
			}catch (IOException e)
			{
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(reader != null)
				try{
					reader.close();
				}catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		return content.toString();
	}

	public void saveNet()
	{
		try {
			FileOutputStream outStream=this.openFileOutput("a.txt",Context.MODE_WORLD_READABLE);
//			outStream.write(text.getText().toString().getBytes());
//			outStream.close();
			Toast.makeText(TextSave.this,"Saved",Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			return;
		}
	}

	public void loadNet()
	{
		try {
			FileInputStream inStream=this.openFileInput("a.txt");
			ByteArrayOutputStream stream=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int length=-1;
			while((length=inStream.read(buffer))!=-1)   {
				stream.write(buffer,0,length);
			}
			stream.close();
			inStream.close();
			//text.setText(stream.toString());
			Toast.makeText(TextSave.this,"Loaded",Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			return ;
		}
	}



}
