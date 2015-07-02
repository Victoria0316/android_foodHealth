package com.cow205.mycontribution;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import  com.cow205.air_health.*;

public class DateAdapter extends BaseAdapter {
	private Context context;
	private List<DateText> list;

	public DateAdapter(Context context, List<DateText> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if (list == null) {
			return null;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_listview, parent, false);
			holder.date = (TextView) convertView
					.findViewById(R.id.txt_date_time);
			holder.content = (TextView) convertView
					.findViewById(R.id.txt_date_content);
			holder.title = (RelativeLayout) convertView
					.findViewById(R.id.rl_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 时间轴竖线的layout
		// 第一条数据，肯定显示时间标题
		if (position == 0) {
			holder.title.setVisibility(View.VISIBLE);
			holder.date.setText(TimeFormat.format("yyyy.MM.dd",
					list.get(position).getDate()));
		} else {          // 不是第一条数�?					// 本条数据和上�?��数据的时间戳相同，时间标题不显示
			if (list.get(position).getDate()
					.equals(list.get(position - 1).getDate())) {
				holder.title.setVisibility(View.GONE);
			} else {
				// 本条数据和上�?��的数据的时间戳不同的时�?，显示数�?				holder.title.setVisibility(View.VISIBLE);
				holder.date.setText(TimeFormat.format("yyyy.MM.dd",
						list.get(position).getDate()));
			}
		}
		holder.content.setText(list.get(position).getText());
		return convertView;
	}

	public static class ViewHolder {
		RelativeLayout title;
		TextView date;
		TextView content;
	}
}