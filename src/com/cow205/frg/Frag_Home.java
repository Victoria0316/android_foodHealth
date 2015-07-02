package com.cow205.frg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

import com.cow205.act.FoodInfoDetail;
import com.cow205.air_health.*;
import com.cow205.constant.ConstantString;
import com.cow205.mycontribution.DateAdapter;
import com.cow205.mycontribution.DateText;
import com.cow205.tools.Efood;
import com.cow205.tools.Efoodinfo;
import com.cow205.tools.Food;
import com.cow205.tools.Food1;
import com.cow205.tools.Foodjianjie;

public class Frag_Home extends Fragment {
	View view;
	private ProgressDialog progressDialog = null;
	// 时间轴列表
	private ListView lvList;
	// 数据list
	private List<DateText> list;
	// 列表适配器
	private DateAdapter adapter;

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println(msg.what);
				progressDialog.cancel();
				initData();
				break;
			}
		}
	};

	private static final int[] resId = { R.drawable.radish, R.drawable.celery,
			R.drawable.leek, R.drawable.spinach, R.drawable.lettuce,
			R.drawable.bamboo, R.drawable.tomato, R.drawable.foreignonion,
			R.drawable.vinegar, R.drawable.tea, R.drawable.beanmilk,
			R.drawable.brownsuger, R.drawable.honey, R.drawable.milk,
			R.drawable.whitespirit, R.drawable.beer, R.drawable.pork,
			R.drawable.pigliver, R.drawable.pigblood, R.drawable.lamb,
			R.drawable.beef, R.drawable.beefliver, R.drawable.goose,
			R.drawable.rabbit, R.drawable.dog, R.drawable.duck,
			R.drawable.chicken, R.drawable.donkey, R.drawable.egg,
			R.drawable.carp, R.drawable.yellowfish, R.drawable.shrimp,
			R.drawable.shrimp2, R.drawable.crab, R.drawable.clam,
			R.drawable.turtle, R.drawable.riversnail, R.drawable.garlic,
			R.drawable.onion };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fra_home_detail, null);
		InitView();
		GetData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resId.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ImageView01", resId[i]);
			map.put("TextView01", ConstantString.food_array[i]);
			map.put("TextView02", ConstantString.food1_array[i]);
			lists.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lists,
				R.layout.list_view_row, new String[] { "ImageView01",
						"TextView01", "TextView02" }, new int[] {
						R.id.ImageView01, R.id.TextView01, R.id.TextView02 });
		lvList.setAdapter(adapter);
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
				for (int j = 0; j < ConstantString.efood_array.length; j++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(ConstantString.efood_array[j],
							ConstantString.etfoodinfo_array[j]);
					data.add(map);
				}
				Intent intent = new Intent();
				intent.setClass(getActivity(), FoodInfoDetail.class);
				intent.putExtra("drawable", resId[position]);
				intent.putExtra("foodname", ConstantString.food_array[position]);
				intent.putExtra("efoodnema",
						ConstantString.food1_array[position]);
				intent.putExtra("foodinfo",
						ConstantString.etfoodinfo_array[position]);
				startActivity(intent);
			}
		});
	}

	private void GetData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(getActivity(), "请稍等...",
				"获取数据中...", true);
		new MyThread().start();

		BmobQuery<Efood> efood = new BmobQuery<Efood>();
		efood.getObject(getActivity(), ConstantString.Efood,
				new GetListener<Efood>() {

					@Override
					public void onSuccess(Efood efood) {
						// TODO Auto-generated method stub
						// System.out.println("---------------------"
						// + listview_first_title.getName());
						ConstantString.efood_array = efood.getEfood()
								.split("/");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
		BmobQuery<Efoodinfo> efoodinfo = new BmobQuery<Efoodinfo>();
		efoodinfo.getObject(getActivity(), ConstantString.Efoodinfo,
				new GetListener<Efoodinfo>() {

					@Override
					public void onSuccess(Efoodinfo efoodinfo) {
						// TODO Auto-generated method stub
						// System.out.println("---------------------"
						// + listview_sec_title.getName());
						ConstantString.etfoodinfo_array = efoodinfo
								.getEfoodinfo().split("/");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
		BmobQuery<Food> food = new BmobQuery<Food>();
		food.getObject(getActivity(), ConstantString.Food,
				new GetListener<Food>() {

					@Override
					public void onSuccess(Food food) {
						// TODO Auto-generated method stub
						// System.out.println("---------------------"
						// + etfoodinfo.getName());
						ConstantString.food_array = food.getFood().split("/");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
		BmobQuery<Food1> food1 = new BmobQuery<Food1>();
		food1.getObject(getActivity(), ConstantString.Food1,
				new GetListener<Food1>() {

					@Override
					public void onSuccess(Food1 food1) {
						// TODO Auto-generated method stub
						// System.out.println("---------------------"
						// + etfoodinfo.getName());
						ConstantString.food1_array = food1.getFood1()
								.split("/");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
		BmobQuery<Foodjianjie> jianjie = new BmobQuery<Foodjianjie>();
		jianjie.getObject(getActivity(), ConstantString.Foodjianjie,
				new GetListener<Foodjianjie>() {

					@Override
					public void onSuccess(Foodjianjie jianjie) {
						// TODO Auto-generated method stub
						ConstantString.foodjianjie_array = jianjie
								.getFoodjianjie().split("/");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void InitView() {
		// TODO Auto-generated method stub
		lvList = (ListView) view.findViewById(R.id.lv_list);
	}

	public class MyThread extends Thread {
		@Override
		public void run() {
			while (true) {
				if ((ConstantString.efood_array != null)
						&& (ConstantString.etfoodinfo_array != null)
						&& (ConstantString.food_array != null)
						&& (ConstantString.food1_array != null)) {
					myHandler.sendEmptyMessage(1);
					break;
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
