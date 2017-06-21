package com.lcmf.xll.recyclerviewdemo.FileIONet;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class FileManager {

	public List<String> mFilePath = new ArrayList<>();

	//constructor
	public FileManager() {
	}

	//遍历文件夹
	public void GetFilePath(File FilePath)
	{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return;
		File files[] = FilePath.listFiles();
		if(files != null)
		{
			for(File f : files)
			{
				//reverse
				if(f.isDirectory())
				{
					GetFilePath(f);
				}
				else
				{
					mFilePath.add(f.toString());
				}
			}
		}
	}

}
