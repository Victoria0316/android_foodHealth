package com.cow205.act;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.SaveListener;

import  com.cow205.air_health.*;
import com.cow205.constant.ConstantString;

public class ActLogin extends Activity implements OnClickListener {
	private EditText et_username, et_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		BmobChat.getInstance(this).init(ConstantString.ApplicationId);
		InitView();
		InitUserInfo();

	}

	private void InitUserInfo() {
		// TODO Auto-generated method stub
		SharedPreferences preferences = getSharedPreferences(
				ConstantString.Login_State, MODE_PRIVATE);
		if (preferences.getString("state", "") == null) {
			et_username.setText("");
			et_pwd.setText("");
		}
		if (preferences.getString("state", "").equals("")) {
			et_username.setText("");
			et_pwd.setText("");
		}
		if (preferences.getString("state", "").equals("0")) {
			SharedPreferences sp = getSharedPreferences(
					ConstantString.Uer_Info, MODE_PRIVATE);
			et_username.setText(sp.getString("User_Name", ""));
			et_pwd.setText(sp.getString("Pwd", ""));
		}
		if (preferences.getString("state", "").equals("1")) {
			finish();
			startActivity(new Intent(ActLogin.this,ActFirst.class));
		}
	}
	private void InitView() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_pwd = (EditText) findViewById(R.id.et_password);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			Login();
			break;

		case R.id.btn_register:
			startActivity(new Intent(ActLogin.this, ActRegister.class));
			break;
		}
	}

	private void Login() {
		// TODO Auto-generated method stub
		final BmobUserManager userManager = new BmobUserManager();
		userManager.init(this);
		if (et_username.getText().toString().equals("")) {
			Toast.makeText(ActLogin.this, "请输入用户名", Toast.LENGTH_LONG).show();
			return;
		}
		if (et_pwd.getText().toString().equals("")) {
			Toast.makeText(ActLogin.this, "请输入密码", Toast.LENGTH_LONG).show();
			return;
		}
		userManager.login(et_username.getText().toString(), et_pwd.getText()
				.toString(), new SaveListener() {

			@Override
			public void onSuccess() {
				// 获取当前登录用户信息
				// System.out.println(userManager.getCurrentUser().getUsername());
				// 省略其他代码
				Toast.makeText(ActLogin.this, "登录成功", Toast.LENGTH_LONG).show();
				SharedPreferences preferences = getSharedPreferences(
						ConstantString.Login_State, MODE_PRIVATE);
				Editor edit = preferences.edit();
				edit.putString("state", "1");
				edit.commit();
				SharedPreferences preferences2 = getSharedPreferences(
						ConstantString.Uer_Info, MODE_PRIVATE);
				Editor edit2 = preferences2.edit();
				edit2.putString("User_Name", et_username.getText().toString());
				edit2.putString("Pwd", et_pwd.getText().toString());
				edit2.commit();
				startActivity(new Intent(ActLogin.this, ActFirst.class));
				finish();
			}

			@Override
			public void onFailure(int errorcode, String arg0) {
				// 登录失败
				Toast.makeText(ActLogin.this, arg0, Toast.LENGTH_LONG).show();
			}
		});
	}
}
