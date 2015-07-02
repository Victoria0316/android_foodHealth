package com.cow205.act;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.cow205.air_health.*;
import com.cow205.constant.ConstantSettingMenu;

public class ActLocation extends Activity implements OnClickListener {
	private MapView mMapView = null;
	private LocationClient mLocationClient;
	private GeofenceClient mGeofenceClient;
	private MyLocationListener mMyLocationListener;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private Vibrator mVibrator;
	public BaiduMap mBaiduMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(this);// ע��sdk
		setContentView(R.layout.act_location);
		InitView();// ��ʼ���ؼ�
		SetMapAttribute();// ���õ�ͼ����
		InitService();// ��ʼ������
		InitLocation();// ��λ��������
	}

	private void SetMapAttribute() {
		// TODO Auto-generated method stub
		mBaiduMap = mMapView.getMap();// ��õ�ͼ
		SharedPreferences preferences = getSharedPreferences(
				ConstantSettingMenu.MapMode_Name, MODE_PRIVATE);
		if (preferences.getString(ConstantSettingMenu.MapMode_Name, "").equals(
				"")) {

			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// ��ͨ
		} else {
			if (preferences.getString(ConstantSettingMenu.MapMode_Name, "")
					.equals("0")) {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// ��ͨ
			} else {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// ����

			}
		}
	}

	private void InitService() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(ActLocation.this);// ��ʼ����λ
		mLocationClient.registerLocationListener(new MyLocationListener());// ע������¼�
		mGeofenceClient = new GeofenceClient(ActLocation.this);
		mVibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
	}

	private void InitView() {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// ���ö�λģʽ
		option.setCoorType("gcj02");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span = 1000;
		try {
			SharedPreferences preferences = getSharedPreferences(
					ConstantSettingMenu.MapMode_DelayTime, MODE_PRIVATE);
			if (preferences
					.getString(ConstantSettingMenu.MapMode_DelayTime, "")
					.equals("")) {
				span = Integer.valueOf("5000");
			} else {
				span = Integer.valueOf(preferences.getString(
						ConstantSettingMenu.MapMode_DelayTime, ""));

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(false);// �Ƿ񷴵������
		mLocationClient.setLocOption(option);// ��������

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();// ֹͣ��λ
		mMapView.onDestroy();// ���ٵ�ͼ
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			// ������λͼ��
			mBaiduMap.setMyLocationEnabled(true);
			// ���춨λ����
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// ���ö�λ����
			mBaiduMap.setMyLocationData(locData);
			// ���ö�λͼ������ã���λģʽ���Ƿ���������Ϣ���û��Զ��嶨λͼ�꣩
			MyLocationConfiguration config = new MyLocationConfiguration(
					com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING,
					true, null);
			mBaiduMap.setMyLocationConfigeration(config);
			// ������Ҫ��λͼ��ʱ�رն�λͼ��
			mBaiduMap.setMyLocationEnabled(true);

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			// logMsg(sb.toString());
		}
	}

	public void logMsg(String str) {
		System.out.println(str);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_btn_back:
			finish();
			break;
		// case R.id.btn_Genral:
		// // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // ��ͨ��ͼ
		// break;
		// case R.id.btn_Real:
		// // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// ���ǵ�ͼ
		// break;
		case R.id.btn_StartLocation:
			mLocationClient.start();// ��ʼ��λ
			break;
		}
	}
}
