package com.cqhot.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.cqhot.R;
import com.cqhot.adapter.MemberItemGridViewAdapter;

public class MemberActivity extends Activity implements OnItemClickListener,OnClickListener{
	private int[] viewId={R.id.gridview_ItemImage,R.id.gridview_ItemText};
	private String[] dataName={"image","title"};
	List<Map<String,Object>> list;

	private GridView mGridView;
	private BaseAdapter mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member);
		mGridView = (GridView) findViewById(R.id.GridView);
		mGridView.setOnItemClickListener(this);
		mAdapter = new MemberItemGridViewAdapter(getApplicationContext(), R.layout.member_item, viewId,getItemList(), dataName);
		mGridView.setAdapter(mAdapter);
	}
	private List<Map<String,Object>> getItemList(){
		list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put(dataName[0],R.drawable.user_center);
		map1.put(dataName[1],"个人账户");
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put(dataName[0],R.drawable.user_shangpu);
		map2.put(dataName[1],"积分商铺");
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put(dataName[0],R.drawable.user_pinggu);
		map3.put(dataName[1],"二手车评估");
		Map<String,Object> map4=new HashMap<String,Object>();
		map4.put(dataName[0],R.drawable.user_tuijian);
		map4.put(dataName[1],"车友推荐");
		Map<String,Object> map5=new HashMap<String,Object>();
		map5.put(dataName[0],R.drawable.user_dian);
		map5.put(dataName[1],"广本长怡店");
		Map<String,Object> map6=new HashMap<String,Object>();
		map6.put(dataName[0],R.drawable.user_fankui);
		map6.put(dataName[1],"意见反馈");
		Map<String,Object> map7=new HashMap<String,Object>();
		map7.put(dataName[0],R.drawable.user_outdriver);
		map7.put(dataName[1],"出行指南");
		Map<String,Object> map8=new HashMap<String,Object>();
		map8.put(dataName[0],R.drawable.user_youhao);
		map8.put(dataName[1],"油耗统计");
		Map<String,Object> map9=new HashMap<String,Object>();
		map9.put(dataName[0],R.drawable.user_weizhangchaxun);
		map9.put(dataName[1],"违章查询");
		Map<String,Object> map10=new HashMap<String,Object>();
		map10.put(dataName[0],R.drawable.user_baoxian);
		map10.put(dataName[1],"保险服务");
		Map<String,Object> map11=new HashMap<String,Object>();
		map11.put(dataName[0],R.drawable.user_weixiululi);
		map11.put(dataName[1],"维修履历");
		Map<String,Object> map12=new HashMap<String,Object>();
		map12.put(dataName[0],R.drawable.user_message);
		map12.put(dataName[1],"消息提醒");
		Map<String,Object> map13=new HashMap<String,Object>();
		map13.put(dataName[0],R.drawable.user_coupon);
		map13.put(dataName[1],"优惠劵");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		list.add(map10);
		list.add(map11);
		list.add(map12);
		list.add(map13);
		return list;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//		@SuppressWarnings("unchecked")
//		Map<String, Object> map = (Map<String, Object>) mAdapter.getItem(position);
//		intent= new Intent();   
//		intent.setClass(this,ItemDetailActivity.class);
//		intent.putExtra("image",(String)map.get("image"));
//		intent.putExtra("memo",(String)map.get("memo"));
//		intent.putExtra("title",(String)map.get("title"));
//		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.gone_list_activity:
//			intent=new Intent();
//			intent.setClass(this,MainTabActivity.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//			finish();
//			break;
//		default:
//			break;
//		}
	}
}
