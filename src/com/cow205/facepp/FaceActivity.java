package com.cow205.facepp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cow205.air_health.R;
import com.facepp.error.FaceppParseException;

public class FaceActivity extends Activity implements OnClickListener {
    private static final int PICK_CODE = 0X110;
	private ImageView mPhoto;
	private Button mGetImage;
	private Button mDetect;
	private View mWaitting;
	private String mCurrentPhotoStr;
	private TextView mTip; 
	private Bitmap mPhotoImage;
	private Paint mPaint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		mPaint = new Paint();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		mGetImage.setOnClickListener(this);
		mDetect.setOnClickListener(this);
	
	}
	//接受activity的结果
  @Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	// TODO Auto-generated method stub
	  if(requestCode == PICK_CODE)
	  {
		  if(intent != null)
		  {
			  Uri uri = intent.getData();
			  Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			  cursor.moveToFirst();
			  
			  int idx =  cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			  mCurrentPhotoStr = cursor.getString(idx);
			  cursor.close();
			  
			  resizePhoto();//compress picture
			  mPhoto.setImageBitmap(mPhotoImage);
			  mTip.setText("click detect ===>");
		  }
	  }
	super.onActivityResult(requestCode, resultCode,intent);
}
	private void resizePhoto() {
	// TODO Auto-generated method stub
	 BitmapFactory.Options options = new BitmapFactory.Options();
	 options.inJustDecodeBounds = true;
	 
	 BitmapFactory.decodeFile(mCurrentPhotoStr,options);
	 double ratio = Math.max(options.outWidth * 1.0d / 1024f,options.outHeight * 1.0d / 1024f);
	 options.inSampleSize = (int) Math.ceil(ratio);
	 options.inJustDecodeBounds = false;
	 mPhotoImage = BitmapFactory.decodeFile(mCurrentPhotoStr,options);
}

	private void initView() {
		// TODO Auto-generated method stub
		mPhoto = (ImageView)findViewById(R.id.id_photo);
		mGetImage = (Button)findViewById(R.id.id_getImage);
		mDetect = (Button)findViewById(R.id.id_detect);
		mTip = (TextView)findViewById(R.id.id_tip);
	    mWaitting = findViewById(R.id.id_waitting);
	}
	
	private static final int MSG_SUCCESS = 0X111;
	private static final int MSG_ERROR = 0X112;
    private Handler mhandler = new Handler(){
       @Override
		public void handleMessage(Message msg) {
    	   switch (msg.what)
    	   {
           	case MSG_SUCCESS:
          	 mWaitting.setVisibility(View.GONE);
          	 JSONObject rs =  (JSONObject) msg.obj;
          	 preperRsBitmap(rs);
          	 mPhoto.setImageBitmap(mPhotoImage);
          	 break;
           case MSG_ERROR:
          	 mWaitting.setVisibility(View.GONE);
          	 String errorMsg = (String) msg.obj;
          	 if(TextUtils.isEmpty(errorMsg)){
          		 mTip.setText("error.");
          	 }else{
          		 mTip.setText(errorMsg);
          	 }
          	 break;
           }
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
       
		private void preperRsBitmap(JSONObject rs) {
			Bitmap bitmap = Bitmap.createBitmap(mPhotoImage.getWidth(),mPhotoImage.getHeight(),mPhotoImage.getConfig());
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(mPhotoImage, 0, 0,null );
			// TODO Auto-generated method stub
			try {
				JSONArray faces = rs.getJSONArray("face");
				int faceCount = faces.length();
				mTip.setText("find"+faceCount);
				for(int i = 0 ; i< faceCount;i++){
					//dandu face duxiang
					JSONObject face  =  faces.getJSONObject(i);
					JSONObject posObj = face.getJSONObject("position");
					System.out.print("face position"+posObj);
					float x = (float)posObj.getJSONObject("center").getDouble("x");
					float y = (float) posObj.getJSONObject("center").getDouble("y");
					float w = (float) posObj.getDouble("width");
					float h = (float)posObj.getDouble("height");
					
					x = x / 100 * bitmap.getWidth();
					y = y / 100 * bitmap.getHeight();
					w = w / 100 * bitmap.getWidth();
					h = h / 100 * bitmap.getHeight();
					
					mPaint.setColor(0xffffffff);
					mPaint.setStrokeWidth(3);
					
					//hua box
					canvas.drawLine(x - w / 2 , y - h / 2 , x - w / 2 , y + h / 2,mPaint);
					canvas.drawLine(x - w / 2 , y - h / 2 , x + w / 2 , y - h / 2,mPaint);
					canvas.drawLine(x + w / 2 , y - h / 2 , x + w / 2 , y + h / 2,mPaint);
					canvas.drawLine(x - w / 2 , y + h / 2 , x + w / 2 , y + h / 2,mPaint);
					//get age and gender
					int age = face.getJSONObject("attribute").getJSONObject("age").getInt("value");
					String gender = face.getJSONObject("attribute").getJSONObject("gender").getString("value");
					
					Bitmap ageBitmap = buildAgeBitmap(age,"Male".equals(gender));
					
					int ageWidth = ageBitmap.getWidth();
					int ageHight = ageBitmap.getHeight();
					if (bitmap.getWidth() < mPhoto.getWidth() && bitmap.getHeight() < mPhoto.getHeight())
					{
						float ratio = Math.max(bitmap.getWidth()*1.0f /mPhoto.getWidth(),
								bitmap.getHeight()*1.0f / mPhoto.getHeight());
						ageBitmap = Bitmap.createScaledBitmap(ageBitmap,(int)(ageWidth * ratio), (int)(ageHight * ratio), false);
						
					}
					canvas.drawBitmap(ageBitmap, x - ageBitmap.getWidth() / 2,y - h / 2 - ageBitmap.getHeight(),null);
					mPhotoImage = bitmap;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private Bitmap buildAgeBitmap(int age, boolean isMale) {
			// TODO Auto-generated method stub
			TextView tv = (TextView) mWaitting.findViewById(R.id.id_age_and_gender);
			tv.setText(age + "");
			if(isMale){
				tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male),null, null,null);
			}else
			{
				tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female),null ,null , null);
			}
			tv.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(tv.getDrawingCache());
			tv.destroyDrawingCache();
			return bitmap;
		}
    };
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.id_getImage:
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, PICK_CODE);
				break;
			case R.id.id_detect:
				mWaitting.setVisibility(View.VISIBLE);
				if(mCurrentPhotoStr !=null && !mCurrentPhotoStr.trim().equals("")){
					 resizePhoto();
				}else{
					mPhotoImage = BitmapFactory.decodeResource(getResources(), R.drawable.t4);
				}
				FaceppDetect.detect(mPhotoImage,new FaceppDetect.CallBack(){
					
					@Override
					public void success(JSONObject result) {
						// TODO Auto-generated method stub
						Message msg = Message.obtain();
						msg.what = MSG_SUCCESS;
						msg.obj = result;
						mhandler.sendMessage(msg);
					}
					
					@Override
					public void error(FaceppParseException exception) {
						// TODO Auto-generated method stub
						Message msg = Message.obtain();
						msg.what = MSG_ERROR;
						msg.obj = exception.getErrorMessage();
						mhandler.sendMessage(msg);
					}
				});
				break;
		}
	}

}
