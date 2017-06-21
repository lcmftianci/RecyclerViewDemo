package com.lcmf.xll.recyclerviewdemo.robot;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class HttpUtils {
	//declare variable
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "20fe5e4db9f94a25be03592bed8a5262";

	public static ChatMessage sendChatMessage(String msg)
	{
		ChatMessage chatMessage = new ChatMessage();
		String jsonRes = doGet(msg);
		Gson gson = new Gson();
		Result result = null;
		try
		{
			result = gson.fromJson(jsonRes, Result.class);
			chatMessage.setMsg(result.getText());
		}catch (JsonSyntaxException e)
		{
			chatMessage.setMsg("服务器繁忙, 请稍候再试");
			e.printStackTrace();
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(ChatMessage.Type.INCOMING);
		return chatMessage;
	}

	//get res
	public static String doGet(String msg)
	{
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream os = null;
		try
		{
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection)urlNet.openConnection();

			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			os = new ByteArrayOutputStream();
			while((len = is.read(buf)) != -1)
			{
				os.write(buf, 0, len);
			}
			os.flush();
			result = new String(os.toByteArray());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}catch (IOException e)
		{
			e.printStackTrace();
			try
			{
				if(os != null)
					os.close();
				if(is != null)
					is.close();
			}
			catch (IOException el)
			{
				el.printStackTrace();
			}
		}

		return result;
	}

	//combine string
	private static String setParams(String msg)
	{
		String strRes = "";
		try
		{
			strRes = URL + "?key=" + API_KEY +"&info=" + URLEncoder.encode(msg, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return strRes;
	}
}
