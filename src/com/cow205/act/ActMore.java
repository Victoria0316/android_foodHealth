package com.cow205.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.bmob.im.BmobUserManager;

import  com.cow205.air_health.*;
import com.cow205.constant.ConstantSettingMenu;
import com.cow205.constant.ConstantString;

public class ActMore extends Activity implements OnClickListener {
	private String Map_Mode = "0";
	private String Map_Delay_Time = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_more);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_btn_back:
			finish();
			break;
		case R.id.rl_act_more_mapmodesetting:
			ChoicMapModen();
			break;
		case R.id.rl_act_more_locationtime:
			DelayTime();
			break;
		case R.id.rl_act_more_version:
			ShowCopyRight();
			break;
		case R.id.btn_logout:
			LoginOut();
			break;
		}
	}

	private void ShowCopyRight() {
		startActivity(new Intent(ActMore.this, ActMyVersion.class));
	}

	private void LoginOut() {
		// TODO Auto-generated method stub
		SharedPreferences preferences = getSharedPreferences(
				ConstantString.Login_State, MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString("state", "0");
		edit.commit();
		setResult(ConstantString.RESULT_OF_LOGOUT);
		BmobUserManager.getInstance(this).logout();// 网络上实现登出
		finish();
	}

	private void DelayTime() {
		// TODO Auto-generated method stub
		int checkedItem;
		AlertDialog.Builder id = new AlertDialog.Builder(ActMore.this);
		id.setTitle("定位时间间隔(秒)");
		final String[] items = { "5000", "10000", "15000", "20000" };

		SharedPreferences sp = getSharedPreferences(
				ConstantSettingMenu.MapMode_DelayTime, MODE_PRIVATE);
		if (sp.getString(ConstantSettingMenu.MapMode_DelayTime, "").equals("")) {
			checkedItem = 0;
		} else {
			int i;
			for (i = 0; i < items.length; i++) {
				if (items[i].equals(sp.getString(
						ConstantSettingMenu.MapMode_DelayTime, ""))) {
					break;
				}
			}
			checkedItem = i;
		}
		String time[] = { "5", "10", "15", "20" };
		id.setSingleChoiceItems(time, checkedItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Map_Delay_Time = items[which];
					}
				});
		id.setPositiveButton("确定", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences(
						ConstantSettingMenu.MapMode_DelayTime, MODE_PRIVATE);
				Editor edit = preferences.edit();
				edit.putString(ConstantSettingMenu.MapMode_DelayTime,
						Map_Delay_Time);
				edit.commit();
			}
		});
		id.setNegativeButton("取消", null);
		id.create();
		id.show();
	}

	private void ChoicMapModen() {
		// TODO Auto-generated method stub
		int checkedItem;
		AlertDialog.Builder id = new AlertDialog.Builder(ActMore.this);
		id.setTitle("地图模式");
		CharSequence items[] = {
				getResources().getString(R.string.choice_item_mapmodem_general),
				getResources().getString(R.string.choice_item_mapmodem_real) };
		SharedPreferences sp = getSharedPreferences(
				ConstantSettingMenu.MapMode_Name, MODE_PRIVATE);
		if (sp.getString(ConstantSettingMenu.MapMode_Name, "").equals("")) {
			checkedItem = 0;
			Map_Mode = "0";
		} else {
			checkedItem = Integer.parseInt(sp.getString(
					ConstantSettingMenu.MapMode_Name, ""));
		}

		id.setSingleChoiceItems(items, checkedItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Map_Mode = which + "";
					}
				});
		id.setPositiveButton("确定", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences(
						ConstantSettingMenu.MapMode_Name, MODE_PRIVATE);
				Editor edit = preferences.edit();
				edit.putString(ConstantSettingMenu.MapMode_Name, Map_Mode);
				edit.commit();
			}
		});
		id.setNegativeButton("取消", null);
		id.create();
		id.show();
	}
}
