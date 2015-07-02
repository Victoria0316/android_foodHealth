package com.cow205.act;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import  com.cow205.air_health.*;
import com.cow205.constant.ConstantString;

public class ActWelcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_welcome);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Jump();
			}
		};
		new Handler().postDelayed(r, 3000);

	}

	protected void Jump() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		SharedPreferences preferences = getSharedPreferences(
				ConstantString.Login_State, MODE_PRIVATE);
		if (preferences.getString("state", "").equals("")) {
			intent.setClass(ActWelcome.this, ActLogin.class);
		}
		if (preferences.getString("state", "").equals("0")) {

			intent.setClass(ActWelcome.this, ActLogin.class);
		}
		if (preferences.getString("state", "").equals("1")) {
			intent.setClass(ActWelcome.this, ActFirst.class);
		}
		System.out.println("ssss");
		ActWelcome.this.startActivity(intent);
		finish();
	}
}
