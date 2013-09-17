package com.cqhot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cqhot.R;

public class TrafficRulesActivity extends Activity{
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traffic_rules);
		}
	public void troubleLight(View view){
		intent=new Intent(this,FaultActivity.class);
		startActivity(intent);
	}
	public void tips(View view){
		intent=new Intent(this,TipsActivity.class);
		startActivity(intent);
	}
}

