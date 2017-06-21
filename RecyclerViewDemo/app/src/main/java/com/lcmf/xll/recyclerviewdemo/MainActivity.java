package com.lcmf.xll.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

	private List<Fruit> fruitList = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initFruits();//init fruit info
		//create recyclerview control
		RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

		//setgrid
		StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

		//create manager
		//LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		//recyclerView.setLayoutManager(layoutManager);
		recyclerView.setLayoutManager(gridLayoutManager);

		//set horizontal
		//layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

		//create adapter
		FruitAdaper adapter = new FruitAdaper(fruitList);
		recyclerView.setAdapter(adapter);
	}


//	private void initFruits()
//	{
//		for(int i = 0; i < 4; i++)
//		{
//			Fruit apple = new Fruit("Apple", R.drawable.image_1);
//			fruitList.add(apple);
//
//			Fruit orange = new Fruit("orange", R.drawable.image_2);
//			fruitList.add(orange);
//
//			Fruit banana = new Fruit("banana", R.drawable.image_3);
//			fruitList.add(banana);
//
//			Fruit watermelon = new Fruit("watermelon", R.drawable.image_4);
//			fruitList.add(watermelon);
//		}
//	}

	//瀑布流
	private void initFruits()
	{
		for(int i = 0; i < 4; i++)
		{
			Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.image_1);
			fruitList.add(apple);

			Fruit orange = new Fruit(getRandomLengthName("orange"), R.drawable.image_2);
			fruitList.add(orange);

			Fruit banana = new Fruit(getRandomLengthName("banana"), R.drawable.image_3);
			fruitList.add(banana);

			Fruit watermelon = new Fruit(getRandomLengthName("watermelon"), R.drawable.image_4);
			fruitList.add(watermelon);
		}
	}

	private String getRandomLengthName(String name)
	{
		Random random = new Random();
		int length = random.nextInt(20) - 1;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < length; i++)
		{
			builder.append(name);
		}
		return builder.toString();
	}
}
