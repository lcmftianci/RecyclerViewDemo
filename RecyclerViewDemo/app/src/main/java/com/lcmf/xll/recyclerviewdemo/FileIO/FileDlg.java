package com.lcmf.xll.recyclerviewdemo.FileIO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lcmf.xll.recyclerviewdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class FileDlg extends Activity{

	private static final String ROOT_PATH = "/";
	//存储文件名称
	private ArrayList<String> names = null;
	//存储文件路径
	private ArrayList<String> paths = null;

	//view
	private View view;
	private EditText editText;
	private ListView mList;
	private File mFile;
	private String mPath;

	//save file path history
	private List<String> arrPrePath;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_dlg);

		//file_list
		mList = (ListView)findViewById(R.id.file_list);

		//filepath
		arrPrePath = new ArrayList<String>();

		//display filepath
		showFileDir(ROOT_PATH);

		//save filepath
		arrPrePath.add(ROOT_PATH);

		//clichListener
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mPath = paths.get(position);
				arrPrePath.add(mPath);
				mFile = new File(mPath);

				//runtime permission
				if(ContextCompat.checkSelfPermission(FileDlg.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(FileDlg.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
				}
				else
				{
					// 文件存在并可读
					boolean be = mFile.exists();
					boolean bc = mFile.canRead();
					if (be && bc){
						if (mFile.isDirectory()){
							//显示子目录及文件
							showFileDir(mPath);
						}
						else{
							//处理文件
							fileHandle(mFile);
						}
					}
					//没有权限
					else{
						Resources res = getResources();
						new AlertDialog.Builder(FileDlg.this).setTitle("Message")
								.setMessage(res.getString(R.string.no_permission))
								.setPositiveButton("OK",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								}).show();
					}
				}
			  }
			});
	}

	//rewrite onBackPress
	@Override
	public void onBackPressed() {
		if(arrPrePath.size() >= 2) {
			mPath = arrPrePath.get(arrPrePath.size() - 2);
			arrPrePath.remove(arrPrePath.size() - 2);
			mFile = new File(mPath);

			//runtime permission
			if (ContextCompat.checkSelfPermission(FileDlg.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(FileDlg.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
			} else {
				// 文件存在并可读
				boolean be = mFile.exists();
				boolean bc = mFile.canRead();
				if (be && bc) {
					if (mFile.isDirectory()) {
						//显示子目录及文件
						showFileDir(mPath);
					} else {
						//处理文件
						fileHandle(mFile);
					}
				}
				//没有权限
				else {
					Resources res = getResources();
					new AlertDialog.Builder(FileDlg.this).setTitle("Message")
							.setMessage(res.getString(R.string.no_permission))
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							}).show();
				}
			}
		}
		else
		{
			super.onBackPressed();
		}
	}

	//get permission
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode)
		{
			case 1:
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					// 文件存在并可读
					boolean be = mFile.exists();
					boolean bc = mFile.canRead();
					if (be && bc){
						if (mFile.isDirectory()){
							//显示子目录及文件
							showFileDir(mPath);
						}
						else{
							//处理文件
							fileHandle(mFile);
						}
					}
					//没有权限
					else{
						Resources res = getResources();
						new AlertDialog.Builder(FileDlg.this).setTitle("Message")
								.setMessage(res.getString(R.string.no_permission))
								.setPositiveButton("OK",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								}).show();
					}
				}
				else
				{
					Toast.makeText(FileDlg.this, "You have noPermission", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	//display filepath
	private void showFileDir(String path){
		names = new ArrayList<String>();
		paths = new ArrayList<String>();
		File file = new File(path);
		File[] files = file.listFiles();
		//如果当前目录不是根目录
		if (!ROOT_PATH.equals(path)){
			names.add("@1");
			paths.add(ROOT_PATH);
			names.add("@2");
			paths.add(file.getParent());
		}
		//添加所有文件
		for (File f : files){
			names.add(f.getName());
			paths.add(f.getPath());
		}
		mList.setAdapter(new UseFileAdapter(this,names, paths));
	}

	//对文件进行增删改
	private void fileHandle(final File file){
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 打开文件
				if (which == 0){
					openFile(file);
				}
				//修改文件名
				else if(which == 1){
					LayoutInflater factory = LayoutInflater.from(FileDlg.this);
					view = factory.inflate(R.layout.rename_dlg, null);
					editText = (EditText)view.findViewById(R.id.file_editText);
					editText.setText(file.getName());
					DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String modifyName = editText.getText().toString();
							final String fpath = file.getParentFile().getPath();
							final File newFile = new File(fpath + "/" + modifyName);
							if (newFile.exists()){
								//排除没有修改情况
								if (!modifyName.equals(file.getName())){
									new AlertDialog.Builder(FileDlg.this)
											.setTitle("注意!")
											.setMessage("文件名已存在，是否覆盖？")
											.setPositiveButton("确定", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													if (file.renameTo(newFile)){
														showFileDir(fpath);
														displayToast("重命名成功！");
													}
													else{
														displayToast("重命名失败！");
													}
												}
											})
											.setNegativeButton("取消", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
												}
											})
											.show();
								}
							}
							else{
								if (file.renameTo(newFile)){
									showFileDir(fpath);
									displayToast("重命名成功！");
								}
								else{
									displayToast("重命名失败！");
								}
							}
						}
					};
					AlertDialog renameDialog = new AlertDialog.Builder(FileDlg.this).create();
					renameDialog.setView(view);
					renameDialog.setButton("确定", listener2);
					renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					});
					renameDialog.show();
				}
				//删除文件
				else{
					new AlertDialog.Builder(FileDlg.this)
							.setTitle("注意!")
							.setMessage("确定要删除此文件吗？")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if(file.delete()){
										//更新文件列表
										showFileDir(file.getParent());
										displayToast("删除成功！");
									}
									else{
										displayToast("删除失败！");
									}
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							}).show();
				}
			}
		};
		//选择文件时，弹出增删该操作选项对话框
		String[] menu = {"打开文件","重命名","删除文件"};
		new AlertDialog.Builder(FileDlg.this)
				.setTitle("请选择要进行的操作!")
				.setItems(menu, listener)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}
	//打开文件
	private void openFile(File file){
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}
	//获取文件mimetype
	private String getMIMEType(File file){
		String type = "";
		String name = file.getName();
		//文件扩展名
		String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("wav")){
			type = "audio";
		}
		else if(end.equals("mp4") || end.equals("3gp")) {
			type = "video";
		}
		else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp") || end.equals("gif")){
			type = "image";
		}
		else {
			//如果无法直接打开，跳出列表由用户选择
			type = "*";
		}
		type += "/*";
		return type;
	}
	private void displayToast(String message){
		Toast.makeText(FileDlg.this, message, Toast.LENGTH_SHORT).show();
	}
}
