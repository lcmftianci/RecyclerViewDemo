package com.lcmf.xll.recyclerviewdemo.ScreenRecoder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ScreenCaption extends Activity{
	private Context mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	}

	/**
	 * 获取和保存当前屏幕的截图
	 */
	private void GetandSaveCurrentImage()
	{
		//1.构建Bitmap
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();

		Bitmap Bmp = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888 );

		//2.获取屏幕
		View decorview = this.getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		Bmp = decorview.getDrawingCache();

		String SavePath = getSDCardPath()+"/AndyDemo/ScreenImage";

		//3.保存Bitmap
		try {
			File path = new File(SavePath);
			//文件
			String filepath = SavePath + "/Screen_1.png";
			File file = new File(filepath);
			if(!path.exists()){
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();

				Toast.makeText(mContext, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下", Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取SDCard的目录路径功能
	 * @return
	 */
	private String getSDCardPath(){
		File sdcardDir = null;
		//判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if(sdcardExist){
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}

}
