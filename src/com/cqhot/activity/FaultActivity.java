package com.cqhot.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.cqhot.R;
import com.cqhot.adapter.FaultItemGridViewAdapter;

public class FaultActivity extends Activity implements OnItemClickListener{
	private int[] viewId={R.id.gridview_fault_ItemImage};
	private String[] dataName={"image"};
	List<Map<String,Object>> list;

	private GridView mGridView;
	private BaseAdapter mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fault_gridview);
		mGridView = (GridView) findViewById(R.id.fault_GridView);
		mGridView.setOnItemClickListener(this);
		mAdapter = new FaultItemGridViewAdapter(getApplicationContext(), R.layout.fault_item, viewId,getItemList(), dataName);
		mGridView.setAdapter(mAdapter);
	}
	private List<Map<String,Object>> getItemList(){
		list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put(dataName[0],R.drawable.deng01);
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put(dataName[0],R.drawable.deng02);
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put(dataName[0],R.drawable.deng03);
		Map<String,Object> map4=new HashMap<String,Object>();
		map4.put(dataName[0],R.drawable.deng04);
		Map<String,Object> map5=new HashMap<String,Object>();
		map5.put(dataName[0],R.drawable.deng05);
		Map<String,Object> map6=new HashMap<String,Object>();
		map6.put(dataName[0],R.drawable.deng06);
		Map<String,Object> map7=new HashMap<String,Object>();
		map7.put(dataName[0],R.drawable.deng07);
		Map<String,Object> map8=new HashMap<String,Object>();
		map8.put(dataName[0],R.drawable.deng08);
		Map<String,Object> map9=new HashMap<String,Object>();
		map9.put(dataName[0],R.drawable.deng09);
		Map<String,Object> map10=new HashMap<String,Object>();
		map10.put(dataName[0],R.drawable.deng10);
		Map<String,Object> map11=new HashMap<String,Object>();
		map11.put(dataName[0],R.drawable.deng11);
		Map<String,Object> map12=new HashMap<String,Object>();
		map12.put(dataName[0],R.drawable.deng12);
		Map<String,Object> map13=new HashMap<String,Object>();
		map13.put(dataName[0],R.drawable.deng13);
		Map<String,Object> map14=new HashMap<String,Object>();
		map14.put(dataName[0],R.drawable.deng14);
		Map<String,Object> map15=new HashMap<String,Object>();
		map15.put(dataName[0],R.drawable.deng15);
		Map<String,Object> map16=new HashMap<String,Object>();
		map16.put(dataName[0],R.drawable.deng16);
		Map<String,Object> map17=new HashMap<String,Object>();
		map17.put(dataName[0],R.drawable.deng17);
		Map<String,Object> map18=new HashMap<String,Object>();
		map18.put(dataName[0],R.drawable.deng18);
		Map<String,Object> map19=new HashMap<String,Object>();
		map19.put(dataName[0],R.drawable.deng19);
		Map<String,Object> map20=new HashMap<String,Object>();
		map20.put(dataName[0],R.drawable.deng20);
		Map<String,Object> map21=new HashMap<String,Object>();
		map21.put(dataName[0],R.drawable.deng21);
		Map<String,Object> map22=new HashMap<String,Object>();
		map22.put(dataName[0],R.drawable.deng22);
		Map<String,Object> map23=new HashMap<String,Object>();
		map23.put(dataName[0],R.drawable.deng23);
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
		list.add(map14);
		list.add(map15);
		list.add(map16);
		list.add(map17);
		list.add(map18);
		list.add(map19);
		list.add(map20);
		list.add(map21);
		list.add(map22);
		list.add(map23);
		return list;
	}
	public void back(View view){
		finish();
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
