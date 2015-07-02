package com.cow205.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cow205.act.ActCompute;
import com.cow205.air_health.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ShowAdapter extends BaseAdapter {
	ArrayList<HashMap<String, String>> list;
	Context context;
	int id[];

	public ShowAdapter(Context context,
			ArrayList<HashMap<String, String>> lists, int id[]) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = lists;
		this.id = id;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.list_view_row1, null);
			ViewUtils.inject(holder, arg1);
			arg1.setTag(holder);
			// holder.imageview01 = (ImageView) arg1
			// .findViewById(R.id.ImageView01);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.imageview01.setImageResource(Integer.parseInt(list.get(arg0)
				.get("ImageView01")));
		holder.textview01.setText(list.get(arg0).get("aa"));
		holder.textview02.setText(list.get(arg0).get("dd"));
		System.out.println(list.get(arg0).get("aa") + "======"
				+ list.get(arg0).get("dd"));
		holder.cb_check
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton a, boolean as) {
						// TODO Auto-generated method stub
						ActCompute.change(arg0, as);
					}
				});
		return arg1;

	}

	class ViewHolder {
		@ViewInject(R.id.cb_check)
		CheckBox cb_check;
		@ViewInject(R.id.ImageView01)
		ImageView imageview01;
		@ViewInject(R.id.TextView01)
		TextView textview01;
		@ViewInject(R.id.TextView02)
		TextView textview02;
	}
}
