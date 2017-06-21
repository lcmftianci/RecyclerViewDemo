//package com.lcmf.xll.recyclerviewdemo.FileIONet;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import com.example.util.CustomFileFilter;
//import com.example.util.FileOpreationUitl;
//import com.example.util.FileSort;
//import com.lcmf.xll.recyclerviewdemo.R;
//
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.database.Cursor;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.widget.DrawerLayout;
//import android.util.Log;
//import android.util.SparseBooleanArray;
//import android.view.ActionMode;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView.MultiChoiceModeListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * Created by Administrator on 2017/5/24 0024.
// */
//
//public class FileMainDlg extends Activity implements OnItemClickListener {
//	private ActionBar actionbar;
//	private ActionBarDrawerToggle drawertoggle;
//	private DrawerLayout drawerLayout;
//	private ListView drawerlistview;
//	private String[] drawerdata;
//	private SimpleAdapter adapter;
//	private List<map<String, Object="">> draweralllist = new ArrayList<map<String, Object="">>();
//	private FragmentA fragmentA;
//	private FragmentB fragmentB;
//	private FragmentC fragmentC;
//	private FragmentD fragmentD;
//	private int[] list_image = { R.drawable.fileicon, R.drawable.fileicon,
//			R.drawable.fileicon, R.drawable.fileicon };
//	private static int index = -1;
//	private static File currentpath;
//	private static File SDpath;
//	//  private static File[] mfileData;
//	private static Context mContext;
//	public  static boolean exit;
//	private static boolean copy;
//	private static MenuItem paste;
//	int i=0;
//	private static EditText newFile;
//	private static FileOpreationUitl opreaFile=new FileOpreationUitl();
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		mContext = FileMainDlg.this;
//		initDrawerLayout();
//		//
//		initDrawerContent();
//		//
//		if (null == savedInstanceState) {
//			// 如果为空代表第一次执行
//			fragmentA = new FragmentA();
//			fragmentB = new FragmentB();
//			fragmentC = new FragmentC();
//			fragmentD = new FragmentD();
//			// 动态添加
//			android.app.FragmentManager fg = getFragmentManager();
//			fg.beginTransaction().add(R.id.drawer_frame_a, fragmentA).commit();
//		}
//	}
//	private void initDrawerContent() {
//		drawerdata = getResources().getStringArray(R.array.array);
//		drawerlistview = (ListView) findViewById(R.id.drawer_list);
//		for (int i = 0; i < drawerdata.length; i++) {
//			Map<String, Object=""> drawer_list_date = new HashMap<String, Object="">();
//			drawer_list_date.put("image", list_image[i]);
//			drawer_list_date.put("text", drawerdata[i]);
//			draweralllist.add(drawer_list_date);
//		}
//		adapter = new SimpleAdapter(this, draweralllist, R.layout.drawer_item,
//				new String[] { "image", "text" }, new int[] {
//				R.id.drawer_imageView, R.id.drawer_textView });
//		drawerlistview.setAdapter(adapter);
//		drawerlistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);// 单选模式
//		drawerlistview.setItemChecked(0, true);// 默认选中第一项
//		drawerlistview.setOnItemClickListener(this);
//	}
//	private void initDrawerLayout() {
//		drawerLayout = (DrawerLayout) findViewById(R.id.chouti);
//		actionbar = getActionBar();
//		actionbar.setDisplayHomeAsUpEnabled(true);
//		drawertoggle = new ActionBarDrawerToggle(this,// 活动
//				drawerLayout,// 布局
//				R.drawable.ic_drawer,// 图片资源
//				R.string.drawer_open,// 打开显示文字
//				R.string.app_name) {// 关闭显示文字
//			public void onDrawerOpened(View drawerView) {
//				actionbar.setTitle(R.string.drawer_open);
//				if (index != -1) {
//					actionbar.setTitle(drawerdata[index]);
//				} else {
//					actionbar.setTitle(R.string.app_name);
//				}
//				invalidateOptionsMenu();// 选项菜单重绘改变
//				super.onDrawerOpened(drawerView);
//			}
//			public void onDrawerClosed(View drawerView) {
//				if (index != -1) {
//					actionbar.setTitle(drawerdata[index]);
//				} else {
//					actionbar.setTitle(R.string.app_name);
//				}
//				invalidateOptionsMenu();// 选项菜单重绘改变
//				super.onDrawerClosed(drawerView);
//			}
//		};
//		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
//		drawerLayout.setDrawerListener(drawertoggle);
//	}
//	// 替换抽屉开关上的图片（向上箭头三道杠）
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		drawertoggle.syncState();
//	}
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		paste=menu.findItem(R.id.paste);
//		return true;
//	}
//	// 选项菜单的预处理方法重写
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		// 如果抽屉被打开
//		if (drawerLayout.isDrawerOpen(Gravity.START)) {
//			// 找到这个AcionBarMenu(操作欄菜單)Item把可见性显示为false（即显示为不可见）
//			menu.findItem(R.id.action_menu_new).setVisible(false);
//		}
//		return super.onPrepareOptionsMenu(menu);
//	}
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// 抽屉开关控制Open,Off
//		if (drawertoggle.onOptionsItemSelected(item)) {
//			return true;
//		}
//		switch(item.getItemId()){
//			case R.id.paste:
//				copy=false;
//				fragmentA.Paste();
//				for(;i<fragmenta.filelist.size();i++){ if(fragmenta.filelist.get(i).exists()){="" new="" alertdialog.builder(this)="" .settitle("复制提示")="" .setmessage("已存在文件"+fragmenta.filelist.get(i).getname())="" .setpositivebutton("覆盖",="" onclicklistener()="" {="" public="" void="" onclick(dialoginterface="" dialog,="" int="" which)="" }="" })="" .setnegativebutton("取消",="" null)="" .show();="" opreafile.copydirectory(fragmenta.filelist.get(i),="" currentpath);="" fragmenta.filelist.clear();="" fragmenta.openfile(currentpath);="" log.d("paete-xxxxxxxxxxx",="" "xxxxxxxxxxxx");="" fragmenta.showmessage("执行粘贴");="" break;="" case="" r.id.action_menu_new:="" view="" null);="" newfile="(EditText)view.findViewById(R.id.newFile);" .settitle("新建文件")="" .setview(view)="" .setpositivebutton("确定",="" string="" +fragmenta.showpath.gettext().tostring()+"="" "="" +newfile.gettext().tostring()+"="" ";="" file="" newfile2="new" file(newfile);="" log.d("***********",="" newfile);="" if(!newfile2.exists()){="" newfile2.mkdirs();="" fragmenta.showmessage("执行新建");="" r.id.action_menu_search:="" return="" super.onoptionsitemselected(item);="" onitemclick(adapterview<?=""> parent, View view, int position,
//					long id) {
//						drawerLayout.closeDrawer(Gravity.START);
//						//更新ActionBar(操作栏)的标题
//						actionbar.setTitle(drawerdata[position]);
//						index=position;
//						//抽屉列表按钮
//						switch (position) {
//							case 0:
//								getFragmentManager().beginTransaction().replace(R.id.drawer_frame_a, fragmentA).commit();
//								break;
//							case 1:
//								getFragmentManager().beginTransaction().replace(R.id.drawer_frame_a, fragmentB).commit();
//								break;
//							case 2:
//								getFragmentManager().beginTransaction().replace(R.id.drawer_frame_a, fragmentC).commit();
//								break;
//							case 3:
//								getFragmentManager().beginTransaction().replace(R.id.drawer_frame_a, fragmentD).commit();
//								break;
//							default:
//								break;
//						}
//					}
//					@Override
//					public boolean onKeyDown(int keyCode, KeyEvent event) {
//						if(keyCode==KeyEvent.KEYCODE_BACK){
//							if(currentpath.equals(SDpath)){
//								if(!exit){//exit==false
//									fragmentA.showMessage("在点返回则退出程序！");
//									exit=true;
//								}else{
//									finish();
//								}
//							}else{
//								exit=false;
//								File path=currentpath.getParentFile();
//								fragmentA.openFile(path);
//								fragmentA.showpath.setText(String.valueOf(path));
//								fragmentA.adapter.notifyDataSetChanged();
//							}
//							return true;
//						}
//						return super.onKeyDown(keyCode, event);
//					}
//					public static class FragmentA extends Fragment implements
//							MultiChoiceModeListener, OnItemClickListener {
//						public static TextView showpath;
//						private ListView listview;
//						private FragmentAdapter adapter;
//						//      private File[] files;
//						private File[] mfileData;
//						int n;
//						private List<file> filelist=new ArrayList<file>();
//
//						//下段注释代码表示往Map里面添加文件数据，如果filename为文件夹，则设置为图标icon[1]，否则设置icon[0]
//						//private int[] icon = { R.drawable.wenjian, R.drawable.wenjianjia };
//						//Map<string,object> map=new HaspMap<string,object>();
//						//map.put("keyname",filename.isDirectory?icon[1]:icon[0]);
//						public View onCreateView(LayoutInflater inflater, ViewGroup container,
//						                         Bundle savedInstanceState) {
//							Log.d("onCreateView.........", "onCreateView.........");
//							View view  = inflater.inflate(R.layout.fragment_a, container, false);
//							showpath=(TextView)view.findViewById(R.id.showpath);
//							listview = (ListView) view.findViewById(R.id.listView);
//							//
////          if (mfileData == null) {
////              SDpath = Environment.getExternalStorageDirectory();
////              showpath.setText(String.valueOf(SDpath));
////               mfileData = SDpath.listFiles(new CustomFileFilter()); // 过滤.文件
////               mfileData = FileSort.sortFile(mfileData);//排序
////               openFile(SDpath);
////          }
//							//
//							bindData();
//							return view;
//						}
//						// 绑定数据
//						public void bindData() {
//							if (Environment.getExternalStorageState().equals(
//									Environment.MEDIA_MOUNTED)) {
//								// 判断sd卡是否正常
//								if (mfileData == null) {
//									SDpath = Environment.getExternalStorageDirectory();
//									showpath.setText(String.valueOf(SDpath));
//									getFileData();
//								}
//								//判断文件夹数据组是否取到了数据
//								for (int i = 0; i < mfileData.length; i++) {
//									Log.i("float1", "111111" + mfileData[i] + "");
//								}
//							} else {
//								showMessage("SD卡出现异常");
//							}
//						}
//						private File[] getFileData() {
//							mfileData = SDpath.listFiles(new CustomFileFilter()); // 过滤.文件
//							mfileData = FileSort.sortFile(mfileData);//排序
//							openFile(SDpath);
//							return mfileData;
//						}
//						public void Paste() {
//							if (copy) {
//								paste.setVisible(true);
//								paste.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//							} else {
//								paste.setVisible(false);
//								paste.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//							}
//						}
//						public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//							SparseBooleanArray array = listview.getCheckedItemPositions();
//							switch (item.getItemId()) {
////          case R.id.newfile:
////       q      showMessage(""+"新建");
////              break;
////          case R.id.search:
////              showMessage(""+"搜索");
////              break;
//								case R.id.remove:
//									//这处理下面这个稀疏数组时尽量不要使用静方法态调用和静态变量
//									//静态不能再次调用静态，否则报异常
//									for (int i = 0; i < array.size(); i++) {
//										Log.d("del-4XXXXXXXXXXX", mfileData[array.keyAt(i)].getName().toString());
//										if(array.valueAt(i)==true){
//											n= array.keyAt(i);
//											opreaFile.deleteFile(mfileData[n]);
//										}
//										Log.d("del-XXXXXXXXXXX", currentpath.listFiles().toString());
//										Log.d("del-XXXXXXXXXXX", currentpath.getName().toString());
//										Log.d("del-XXXXXXXXXXX", showpath.getText().toString());
//									}
//									openFile(currentpath);
//									showMessage("删除成功");
////              showMessage("删除"+mfileData[array.keyAt(n)].isFile()?"删除文件成功！":"删除文件夹成功！");
//									mode.finish();
//								case R.id.copy:
//									copy=true;
//									Paste();
//									//屏蔽复制按钮
////              mode.getMenu().findItem(R.id.copy).setVisible(false);
////              //显示粘贴按钮
////              mode.getMenu().findItem(R.id.paste).setVisible(true);
//									for(int i=0;i parent, View view, int position,
//									long id) {
//									File file = mfileData[position];
//									showpath.setText(String.valueOf(file));
//									Log.d("onItem-xxxxxxxxxxxxxx", showpath.getText().toString());
//									if(file.isDirectory()){
//										openFile(file);
//										adapter.notifyDataSetChanged();
//									}else{
//										//打開文件
//										Uri uri = Uri.fromFile(file);
//										Log.d("aaaaaaaaaaaaaa", uri.toString());
//										String[] projection = { MediaStore.Files.FileColumns.MIME_TYPE };
//										Cursor cursor = getActivity().getContentResolver().query(
//												MediaStore.Files.getContentUri("external"), projection,
//												MediaStore.Files.FileColumns.DATA + "=?",
//												new String[] { file.getAbsoluteFile().toString() },
//												null);
//										cursor.moveToNext();
//										String mime = cursor.getString(0);
//										Log.d("bbbbbbbbbbbbbb", mime);
//										// MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//										Intent intent = new Intent(Intent.ACTION_VIEW);
//										intent.setDataAndType(uri, mime);
//										getActivity().startActivity(intent);
//									}
//								}
//							public void openFile(File path) {
//								currentpath=path;
//								mfileData = path.listFiles(new CustomFileFilter()); // 过滤.文件
//								mfileData= FileSort.sortFile(mfileData); // 排序
//								adapter = new FragmentAdapter(mfileData,
//										getActivity());
//								listview.setAdapter(adapter);
//								for (int i = 0; i < mfileData.length; i++) {
//									Log.i("XXXXXXXXXXXX", "111111111111" + mfileData[i] + "");
//								}
////                      showMessage("当前文件夹有"+mfileData.length+"个对象");
//								listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//								listview.setMultiChoiceModeListener(FragmentA.this);
//								listview.setOnItemClickListener(this);
//							}
//							public void showMessage(String info) {
//								Toast.makeText(mContext, info, 1000).show();
//							}
//							public static class FragmentB extends Fragment {
//								private ListView mp3list;
//								private SimpleAdapter mp3adapter;
//								public View onCreateView(LayoutInflater inflater,
//								                         ViewGroup container,
//								                         Bundle savedInstanceState) {
//									View view = inflater.inflate
//											(R.layout.fragment_b, container, false);
//									mp3list=(ListView)view.findViewById(R.id.mp3listView);
//									opreaFile.getReciver(SDpath);
//
//									mp3adapter=new SimpleAdapter(getActivity(),
//											opreaFile.mp3_data,
//											R.layout.mp3_fragment_b,
//											new String[]{opreaFile.mp3_listitem},
//											new int[]{R.id.mp3textView});
//									mp3list.setAdapter(mp3adapter);
//									return view;
//								}
//							}
//							public static class FragmentC extends Fragment {
//								private ListView video_list;
//								private SimpleAdapter video_adapter;
//								public View onCreateView(LayoutInflater inflater,
//								                         ViewGroup container,
//								                         Bundle savedInstanceState) {
//									View view = inflater.inflate
//											(R.layout.fragment_c, container, false);
//									video_list=(ListView)view.findViewById(R.id.video_listView);
//									Log.d("2_XXXXXXXXXX", SDpath.getName().toString());
//									opreaFile.getVideo(SDpath);
//
//									video_adapter=new SimpleAdapter(getActivity(),
//											opreaFile.video_data,
//											R.layout.video_fragment_b,
//											new String[]{opreaFile.video_listitem},
//											new int[]{R.id.video_textView});
//									video_list.setAdapter(video_adapter);
//									return view;
//								}
//							}
//							public static class FragmentD extends Fragment {
//								private ListView picture_list;
//								private SimpleAdapter picture_adapter;
//								public View onCreateView(LayoutInflater inflater,
//								                         ViewGroup container,
//								                         Bundle savedInstanceState) {
//									View view = inflater.inflate
//											(R.layout.fragment_d, container, false);
//									picture_list=(ListView)view.findViewById(R.id.picture_listView);
//									opreaFile.getPicture(SDpath);
//									picture_adapter=new SimpleAdapter(getActivity(),
//											opreaFile.picture_data,
//											R.layout.picture_fragment_b,
//											new String[]{opreaFile.picture_listitem},
//											new int[]{R.id.picture_textView});
//									picture_list.setAdapter(picture_adapter);
//									return view;
//								}
//							}
//}
///*  public static class AsyncTaskTest extends AsyncTask<string, integer,="" integer="">{
//
//        @Override
//        protected Result doInBackground(Params... arg0) {
//
//            return null;
//        }
//
//    }*/
//}
