package com.cqhot.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cqhot.R;
import com.cqhot.adapter.TipsItemListViewAdapter;

public class TipsActivity extends Activity{
	private ListView listview;
	private int[] viewId={R.id.tips_item_leftimg,R.id.tips_item_text,R.id.tips_item_rightimg};
	private String[] dataName={"tips_leftimg","tips_text","tips_rightimg"};
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips);
		listview=(ListView) findViewById(R.id.tips_listview);
		listview.setAdapter(new TipsItemListViewAdapter(getApplicationContext(),R.layout.tips_item,viewId,getTipsListData(),dataName));
	}
	private List<Map<String,Object>> getTipsListData(){
		list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put(dataName[0],R.drawable.xts_bookico);
		map1.put(dataName[1],"节省燃油常识");
		map1.put(dataName[2],R.drawable.right_icon);
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put(dataName[0],R.drawable.xts_bookico);
		map2.put(dataName[1],"车辆保养常识-经常更换机油及三芯片");
		map2.put(dataName[2],R.drawable.right_icon);
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put(dataName[0],R.drawable.xts_bookico);
		map3.put(dataName[1],"车辆保养常识-经常给爱车“体验”");
		map3.put(dataName[2],R.drawable.right_icon);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		return list;
	}
	public void back(View view){
		finish();
	}
	public void call(View view){
		Intent intent=new Intent(this,CallDialogActivity.class);
		startActivity(intent);
	}
}
