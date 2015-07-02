package com.cow205.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.listener.SaveListener;

import  com.cow205.air_health.*;
import com.cow205.constant.ConstantString;

public class ActRegister extends Activity implements OnClickListener {
	private EditText et_username, et_pwd, et_pwd_again, et_email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BmobChat.getInstance(this).init(ConstantString.ApplicationId);
		setContentView(R.layout.act_register);
		InitView();
	}

	private void InitView() {
		// TODO Auto-generated method stub
		et_username = (EditText) findViewById(R.id.et_username);
		et_pwd = (EditText) findViewById(R.id.et_password);
		et_pwd_again = (EditText) findViewById(R.id.et_pwd_again);
		et_email = (EditText) findViewById(R.id.et_email);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_register:
			Register();
			break;
		default:
			break;
		}
	}

	private void Register() {
		// TODO Auto-generated method stub
		// 创建一个用户
		BmobChatUser bu = new BmobChatUser();
		if (et_username.getText().toString().equals("")) {
			Toast.makeText(ActRegister.this, "请输入用户名", Toast.LENGTH_LONG).show();
			return;
		}
		if (et_pwd.getText().toString().equals("")) {
			Toast.makeText(ActRegister.this, "请输入密码", Toast.LENGTH_LONG).show();
			return;
		}
		if (!(et_pwd.getText().toString().equals(et_pwd_again.getText()
				.toString()))) {
			Toast.makeText(ActRegister.this, "两次的密码不一致", Toast.LENGTH_LONG).show();
			return;
		}
		if (et_email.getText().toString().equals("")) {
			Toast.makeText(ActRegister.this, "邮箱不能为空", Toast.LENGTH_LONG).show();
			return;
		} else {
			if (isEmail(et_email.getText().toString())) {

			} else {
				Toast.makeText(ActRegister.this, "邮箱不正确", Toast.LENGTH_LONG).show();
				return;
			}
		}

		bu.setUsername(et_username.getText().toString());
		bu.setPassword(et_pwd.getText().toString());
		bu.setEmail(et_email.getText().toString());
		// 注册
		bu.signUp(ActRegister.this, new SaveListener() {

			@Override
			public void onSuccess() {

				Toast.makeText(ActRegister.this, "注册成功", Toast.LENGTH_LONG)
						.show();
				SharedPreferences sp = getSharedPreferences(
						ConstantString.Login_State, MODE_PRIVATE);
				Editor edit2 = sp.edit();
				edit2.putString("state", "1");
				edit2.commit();

				SharedPreferences preferences = getSharedPreferences(
						ConstantString.Uer_Info, MODE_PRIVATE);
				Editor edit = preferences.edit();
				edit.putString("User_Name", et_username.getText().toString());
				edit.putString("Pwd", et_pwd.getText().toString());
				edit.commit();
				finish();
				startActivity(new Intent(ActRegister.this,
						ActFirst.class));
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				System.out.println("注册失败" + arg1);

			}
		});
	}

	// 邮箱检验
	public static boolean isEmail(String strEmail) {
		Pattern p = Pattern.compile(ConstantString.E_mail);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

}
