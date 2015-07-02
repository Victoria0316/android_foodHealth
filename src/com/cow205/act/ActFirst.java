package com.cow205.act;

import java.util.ArrayList;
import java.util.HashMap;

import com.cow205.air_health.R;
import com.cow205.facepp.FaceppDetect;
import com.cow205.facepp.FaceActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActFirst extends Activity {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i < 7; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i == 1) {
				map.put("ItemImage", R.drawable.sc);
				map.put("ItemText", getResources()
						.getString(R.string.gridview1));
			}
			if (i == 2) {
				map.put("ItemImage", R.drawable.ww);
				map.put("ItemText", getResources()
						.getString(R.string.gridview2));
			}
			if (i == 3) {
				map.put("ItemImage", R.drawable.js);
				map.put("ItemText", getResources()
						.getString(R.string.gridview3));
			}
			if (i == 4) {
				map.put("ItemImage", R.drawable.hc);
				map.put("ItemText", getResources()
						.getString(R.string.gridview4));
			}
			if (i == 5) {
				map.put("ItemImage", R.drawable.zb);
				map.put("ItemText", getResources()
						.getString(R.string.gridview5));
			}
			if (i == 6) {
				map.put("ItemImage", R.drawable.gd);
				map.put("ItemText", getResources()
						.getString(R.string.gridview6));
			}
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.grid_item, new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });

		gridview.setAdapter(saImageItems);
		gridview.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {

		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {

			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);

			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview1))) {
				// Toast.makeText(ActFirst.this, R.string.gridview1,
				// Toast.LENGTH_LONG).show();
				startActivity(new Intent(ActFirst.this, ActMainActivity.class));
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview2))) {
				// Toast.makeText(ActFirst.this, R.string.gridview2,
				// Toast.LENGTH_LONG).show();
				startActivity(new Intent(ActFirst.this, FaceActivity.class));
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview3))) {
				// Toast.makeText(ActFirst.this, R.string.gridview3,
				// Toast.LENGTH_LONG).show();
				startActivity(new Intent(ActFirst.this, ActCompute.class));
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview4))) {
				Toast.makeText(ActFirst.this, R.string.gridview7,
						Toast.LENGTH_LONG).show();
				startActivity(new Intent(ActFirst.this, ActWeather.class));
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview5))) {
				startActivity(new Intent(ActFirst.this, ActLocation.class));
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview6))) {
				Toast.makeText(ActFirst.this, R.string.gridview7,
						Toast.LENGTH_LONG).show();
			    startActivity(new Intent(ActFirst.this, ActWeather.class));
			}
		}
	}
}
