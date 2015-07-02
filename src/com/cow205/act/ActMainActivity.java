package com.cow205.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import  com.cow205.air_health.*;
import com.cow205.constant.ConstantString;
import com.cow205.facepp.FaceActivity;
import com.cow205.frg.Frag_Context;

public class ActMainActivity extends FragmentActivity implements
		OnClickListener {

	private DrawerLayout mDrawerLayout = null;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	public TextView tv_Myname, tv_Title;
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());// 注册sdk
		setContentView(R.layout.act_main);
		InitView();// 初始化布局控件
		initFirstFragment();// 初始化界面的时候加载首页
	}

	private void initFirstFragment() {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		transaction.replace(R.id.content_frame, new Frag_Context());
		mDrawerLayout.closeDrawer(Gravity.LEFT);
		transaction.commit();

	}

	// 左右两侧的菜单栏填充
	private void InitView() {
		// 右边栏菜单
	 //   startActivity(new Intent(ActMainActivity.this,R.layout.act_main));
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.act_face:
			startActivity(new Intent(ActMainActivity.this, FaceActivity.class));
			break;
		case R.id.rl_weather:
		 startActivity(new Intent(ActMainActivity.this, ActWeather.class));
		 break;
		case R.id.iv_btn_showmenu:
			if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} else {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
			break;
		case R.id.rl_more:
			startActivityForResult(new Intent(ActMainActivity.this,ActMore.class), 1);
			break;
		case R.id.rl_location:
			startActivity(new Intent(ActMainActivity.this, ActLocation.class));
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case ConstantString.RESULT_OF_LOGOUT:
			startActivity(new Intent(ActMainActivity.this, ActLogin.class));
			finish();
			break;
		default:
			break;
		}
	}

	// 监听两次返回键
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN) {
//
//			if ((System.currentTimeMillis() - exitTime) > 2000) {
//				Toast.makeText(
//						ActMainActivity.this,
//						getResources().getString(
//								R.string.notice_info_back_logout),
//						Toast.LENGTH_SHORT).show();
//				exitTime = System.currentTimeMillis();
//			} else {
//				finish();
//				System.exit(0);
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
}
