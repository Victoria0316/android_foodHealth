package com.cow205.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  com.cow205.air_health.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FoodInfoDetail extends Activity {
	Button image = null;
	ImageView back = null;
	TextView foodinfo = null;
	String[] efood = { "黄连" };
	String[] efoodinfo = { "猪肉多脂，酸寒滑腻。若中药配方中以黄莲为主时，应忌食猪肉，不然会降低药效，且容易引起腹泻。" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.foodinfo);
		Bundle bundle = getIntent().getExtras();
		int drawable = bundle.getInt("drawable");
		String foodname = bundle.getString("foodname");
//		String efoodname = bundle.getString("efoodnema");
		String foodinfos = bundle.getString("foodinfo");
		image = (Button) findViewById(R.id.Button);
		image.setBackgroundResource(drawable);
		image.setText(foodname);
		foodinfo = (TextView) findViewById(R.id.TextView03);
		ListView list = (ListView) findViewById(R.id.list);
		foodinfo.setText(foodinfos);
		back = (ImageView) findViewById(R.id.iv_btn_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < efood.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TextView04", efood[i]);
			map.put("TextView05", efoodinfo[i]);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.ex_foodinfo,
				new String[] { "TextView04", "TextView05" }, new int[] {
						R.id.TextView04, R.id.TextView05 });
		list.setAdapter(adapter);
	}
}
