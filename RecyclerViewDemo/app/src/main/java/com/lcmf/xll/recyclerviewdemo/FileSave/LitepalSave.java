package com.lcmf.xll.recyclerviewdemo.FileSave;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lcmf.xll.recyclerviewdemo.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class LitepalSave extends Activity implements View.OnClickListener{

	private Button btnCre;
	private Button btnAdd;
	private Button btnUpdate;
	private Button btnDelete;
	private Button btnFind;

	private Button btnUpdateAll;
	private Button btnDelAll;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.litepalsave);
		initView();
		initListener();
	}

	private void initView()
	{
		btnCre = (Button)findViewById(R.id.createdatabase);
		btnAdd = (Button)findViewById(R.id.add);
		btnDelete = (Button)findViewById(R.id.delete);
		btnUpdate = (Button)findViewById(R.id.mod);
		btnFind = (Button)findViewById(R.id.find);
		btnDelAll = (Button)findViewById(R.id.delAll);
		btnUpdateAll = (Button)findViewById(R.id.modAll);

	}

	private void initListener()
	{
		btnCre.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnFind.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnDelAll.setOnClickListener(this);
		btnUpdateAll.setOnClickListener(this);
	}


	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.createdatabase:
				Connector.getDatabase();
				break;
			case R.id.add: {
				Book book = new Book();
				book.setName("Dada");
				book.setAuthor("Lala");
				book.setPages(568);
				book.setPrice(98);
				book.setPress("CHN");
				book.save();
			}
				break;
			case R.id.mod: {
				Book book = new Book();
				book.setName("Dada");
				book.setAuthor("Lala");
				book.setPages(568);
				book.setPrice(98);
				book.setPress("CHN");
				book.save();
				book.setPrice(100);
				book.save();
			}
				break;
			case R.id.modAll:
			{
				Book book = new Book();
				book.setPrice(4.99);
				book.setPress("Anbin");
				book.updateAll("name = ? and author = ?", "Dada", "Ldla");
				break;
			}
			case R.id.delAll:
				DataSupport.deleteAll(Book.class, "price < ?", "15");
				break;

			case R.id.find:
				List<Book> books = DataSupport.findAll(Book.class);
				for(Book book:books)
				{
					Log.d("LitepalSava", "book name is " + book.getName());
				}
				break;
			default:
				break;
		}
	}
}
