package com.cqhot.activity;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.cqhot.R;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	
	private static final long BACK_PRESSED_INTERVAL_MILLIS = 1500;

	private long mLastBackPressedTimeMillis = 0;
	
	private TabHost mHost;
	private Intent mNewsInfo;
	private Intent mTrafficRules;
	private Intent mOnlineBooking;
	private Intent mNifrs;
	private Intent mMember;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.mNewsInfo = new Intent(this, NewsInfoActivity.class);
		this.mTrafficRules = new Intent(this,TrafficRulesActivity.class);
		this.mOnlineBooking = new Intent(this,OnlineBookingActivity.class);
		this.mNifrs= new Intent(this,LocationOverlay.class);
		this.mMember=new Intent(this,MemberActivity.class);
		initRadios();
		setupIntent();
	}
	private void initRadios() {
		 ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
		 ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
		 ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
		 ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);
		 ((RadioButton) findViewById(R.id.radio_button4)).setOnCheckedChangeListener(this);
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
		    switch (buttonView.getId()) {
		    case R.id.radio_button0:
		        this.mHost.setCurrentTabByTag("mNewsInfo_tab");
		        break;
		    case R.id.radio_button1:
		        this.mHost.setCurrentTabByTag("mTrafficRules_tab");
		        break;
		    case R.id.radio_button2:
		        this.mHost.setCurrentTabByTag("mOnlineBooking_tab");
		        break;
		    case R.id.radio_button3:
		        this.mHost.setCurrentTabByTag("mNifrs_tab");
		        break;
		    case R.id.radio_button4:
		        this.mHost.setCurrentTabByTag("mMember_tab");
		        break;
		    }
		}
		
	}
	private void setupIntent() {
		this.mHost = getTabHost();
		TabHost localTabHost = this.mHost;
		
		localTabHost.addTab(buildTabSpec("mNewsInfo_tab",R.string.newsinfo,this.mNewsInfo));
		localTabHost.addTab(buildTabSpec("mTrafficRules_tab",R.string.trafficrules,this.mTrafficRules));
		localTabHost.addTab(buildTabSpec("mOnlineBooking_tab",R.string.onlinebooking,this.mOnlineBooking));
		localTabHost.addTab(buildTabSpec("mNifrs_tab",R.string.nifrs,this.mNifrs));
		localTabHost.addTab(buildTabSpec("mMember_tab",R.string.member,this.mMember));
		}
	private TabHost.TabSpec buildTabSpec(String tag,int label,final Intent content) {
		return this.mHost.newTabSpec(tag).setIndicator(getString(label)).setContent(content);
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {      
	    	final long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - mLastBackPressedTimeMillis > BACK_PRESSED_INTERVAL_MILLIS){
				Toast.makeText(getApplicationContext(), R.string.tip_exit_app, Toast.LENGTH_SHORT).show();
				mLastBackPressedTimeMillis = currentTimeMillis;
				return false;
			}else{
				super.onBackPressed();
				System.exit(0);
			}
	    }
	    return true;
	}
}
