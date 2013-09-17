package com.cqhot.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import com.cqhot.R;

public class CarNifrsActivity extends Activity{
	private ImageButton img_btn;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_nifrs);
		initUI();
	}
	private void initUI(){
		img_btn=(ImageButton) findViewById(R.id.car_img);
	}
	public void back(View view){
		finish();
	}
	public void sendImg(View view){
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(
				CarNifrsActivity.this).setTitle("选择相片");
			    String[] arrayOfString = new String[2];
			    arrayOfString[0] = "拍一张新相片";
			    arrayOfString[1] = "从相册中选取";
			    localBuilder
			      .setItems(arrayOfString,
			        new DialogInterface.OnClickListener() {
			         public void onClick(
			           DialogInterface 
			paramDialogInterface,
			           int paramInt) {
			          switch (paramInt) {
			          default:
			          case 0:
			        	   intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		               	   intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		               	   startActivityForResult(intent, 1);
			           break;
			          case 1:
			        	   intent = new Intent();
		               	   intent.setType("image/*");
		               	   intent.setAction(Intent.ACTION_GET_CONTENT);
		               	   startActivityForResult(intent, 2);
			           break;
			          }
			         }
			        }).setNegativeButton("取消", null).show();
	}
	public void send_call(View view){
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		BitmapFactory.Options options = new BitmapFactory.Options();  options.inSampleSize=2;
		if (resultCode != RESULT_OK) {
			   return;
		}
		if (data != null) {
		   Uri mImageCaptureUri = data.getData();
		   if (mImageCaptureUri != null) {
			    Bitmap image;
			    try {
			    	 image = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(mImageCaptureUri), null, options);
//				     image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
				     if (image != null) {
				    	 img_btn.setImageBitmap(image);
				     }
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
		   } else {
		    Bundle extras = data.getExtras();
			    if (extras != null) {
				     Bitmap image = extras.getParcelable("data");
				     if (image != null) {
				    	 img_btn.setImageBitmap(image);
				     }
			    }
		   }
	  }
	}
	
}
