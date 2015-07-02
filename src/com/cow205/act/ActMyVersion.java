package com.cow205.act;


import  com.cow205.air_health.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActMyVersion extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_myversion);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_btn_back:
			finish();
			break;

		default:
			break;
		}
	}

}
