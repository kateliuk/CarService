package com.cqhot.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cqhot.R;

public class CallDialogActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_dialog);
	}
	public void call(View view){
		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-83557399")));
		finish();
	}
	public void cancel(View view){
		finish();
	}
}
