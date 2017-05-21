package com.lcmf.xll.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<Fruit> fruitList = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFruits();//init fruit info
		//create recyclerview control
		RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

		//create manager
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		//create adapter
		FruitAdaper adapter = new FruitAdaper(fruitList);
		recyclerView.setAdapter(adapter);
	}

	private void initFruits()
	{
		for(int i = 0; i < 4; i++)
		{
			Fruit apple = new Fruit("Apple", R.drawable.image_1);
			fruitList.add(apple);

			Fruit orange = new Fruit("orange", R.drawable.image_2);
			fruitList.add(orange);

			Fruit banana = new Fruit("banana", R.drawable.image_3);
			fruitList.add(banana);

			Fruit watermelon = new Fruit("watermelon", R.drawable.image_4);
			fruitList.add(watermelon);
		}
	}
}
