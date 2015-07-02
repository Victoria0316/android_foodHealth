package com.cow205.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

import com.cow205.adapter.ShowAdapter;
import com.cow205.air_health.R;
import com.cow205.constant.ConstantString;
import com.cow205.tools.Efood;
import com.cow205.tools.Efoodinfo;
import com.cow205.tools.Food;
import com.cow205.tools.Food1;
import com.cow205.tools.Foodjianjie;

public class ActCompute extends Activity implements OnClickListener {
	ListView lv_show;
	Button btn_submit;
	private ProgressDialog progressDialog = null;
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
	static ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_compute);
		initview();
		GetData();
	}

	private void initview() {
		// TODO Auto-generated method stub
		lv_show = (ListView) findViewById(R.id.lv_show);
		btn_submit = (Button) findViewById(R.id.btn_submit);

	}

	private void initData() {
		// TODO Auto-generated method stub

		for (int i = 0; i < resId.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("statue", "");
			map.put("ImageView01", resId[i] + "");
			map.put("aa", ConstantString.food_array[i]);
			map.put("dd", ConstantString.food1_array[i]);
			map.put("random", new Random().nextInt(100) + "");
			lists.add(map);
		}
		// SimpleAdapter adapter = new SimpleAdapter(this, lists,
		// R.layout.list_view_row1, new String[] { "ImageView01",
		// "TextView01", "TextView02" }, new int[] {
		// R.id.ImageView01, R.id.TextView01, R.id.TextView02 });
		ShowAdapter adapter = new ShowAdapter(this, lists, resId);
		lv_show.setAdapter(adapter);
		lv_show.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	private void GetData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(this, "请稍等...", "获取数据中...", true);
		new MyThread().start();

		BmobQuery<Efood> efood = new BmobQuery<Efood>();
		efood.getObject(this, ConstantString.Efood, new GetListener<Efood>() {

			@Override
			public void onSuccess(Efood efood) {
				// TODO Auto-generated method stub
				// System.out.println("---------------------"
				// + listview_first_title.getName());
				ConstantString.efood_array = efood.getEfood().split("/");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		BmobQuery<Efoodinfo> efoodinfo = new BmobQuery<Efoodinfo>();
		efoodinfo.getObject(this, ConstantString.Efoodinfo,
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
		food.getObject(this, ConstantString.Food, new GetListener<Food>() {

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
		food1.getObject(this, ConstantString.Food1, new GetListener<Food1>() {

			@Override
			public void onSuccess(Food1 food1) {
				// TODO Auto-generated method stub
				// System.out.println("---------------------"
				// + etfoodinfo.getName());
				ConstantString.food1_array = food1.getFood1().split("/");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		BmobQuery<Foodjianjie> jianjie = new BmobQuery<Foodjianjie>();
		jianjie.getObject(this, ConstantString.Foodjianjie,
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_submit:
			Submit();
			break;

		default:
			break;
		}
	}

	private void Submit() {
		// TODO Auto-generated method stub
		int temp = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).get("statue").equals("true")) {
				temp = temp + Integer.parseInt(lists.get(i).get("random"));
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("总热量").setMessage(temp + "焦耳(j)")
				.setPositiveButton("确定", null).create().show();
	}

	public static void change(int position, boolean se) {
		System.out.println(position + "   sssss   " + se);
		lists.get(position).put("statue", se + "");
	}
}
