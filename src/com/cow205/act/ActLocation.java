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
		SDKInitializer.initialize(this);// 注册sdk
		setContentView(R.layout.act_location);
		InitView();// 初始化控件
		SetMapAttribute();// 设置地图属性
		InitService();// 初始化服务
		InitLocation();// 定位属性设置
	}

	private void SetMapAttribute() {
		// TODO Auto-generated method stub
		mBaiduMap = mMapView.getMap();// 获得地图
		SharedPreferences preferences = getSharedPreferences(
				ConstantSettingMenu.MapMode_Name, MODE_PRIVATE);
		if (preferences.getString(ConstantSettingMenu.MapMode_Name, "").equals(
				"")) {

			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 普通
		} else {
			if (preferences.getString(ConstantSettingMenu.MapMode_Name, "")
					.equals("0")) {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 普通
			} else {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// 卫星

			}
		}
	}

	private void InitService() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(ActLocation.this);// 初始化定位
		mLocationClient.registerLocationListener(new MyLocationListener());// 注册监听事件
		mGeofenceClient = new GeofenceClient(ActLocation.this);
		mVibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
	}

	private void InitView() {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
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
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(false);// 是否反地理编码
		mLocationClient.setLocOption(option);// 设置属性

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();// 停止定位
		mMapView.onDestroy();// 销毁地图
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);
			// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
			MyLocationConfiguration config = new MyLocationConfiguration(
					com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING,
					true, null);
			mBaiduMap.setMyLocationConfigeration(config);
			// 当不需要定位图层时关闭定位图层
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
				// 运营商信息
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
		// // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 普通地图
		// break;
		// case R.id.btn_Real:
		// // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// 卫星地图
		// break;
		case R.id.btn_StartLocation:
			mLocationClient.start();// 开始定位
			break;
		}
	}
}
