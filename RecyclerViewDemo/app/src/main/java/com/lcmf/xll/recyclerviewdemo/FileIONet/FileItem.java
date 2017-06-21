package com.lcmf.xll.recyclerviewdemo.FileIONet;

import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class FileItem {

	private String strFileName;
	private ImageView imgFileImage;

	public FileItem(String strFileName, ImageView imgFileImage) {
		this.strFileName = strFileName;
		this.imgFileImage = imgFileImage;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public ImageView getImgFileImage() {
		return imgFileImage;
	}

	public void setImgFileImage(ImageView imgFileImage) {
		this.imgFileImage = imgFileImage;
	}

	public FileItem() {
	}
}
