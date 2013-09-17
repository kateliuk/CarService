package com.cqhot.adapter;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TipsItemListViewAdapter extends BasicAdapter{

	public TipsItemListViewAdapter(Context context, int view,
			int[] viewId, List<Map<String, Object>> data, String[] dataName) {
		super(LayoutInflater.from(context), view, viewId, data, dataName);
	}
	class ViewHolder{
		public ImageView tips_leftimg,tips_rightimg;
		public TextView tips_text;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			int[] viewId = getViewId();
			convertView = getInflater().inflate(getView(),null);
			holder.tips_leftimg=(ImageView)convertView.findViewById(viewId[0]);
			holder.tips_text=(TextView) convertView.findViewById(viewId[1]);
			holder.tips_rightimg = (ImageView)convertView.findViewById(viewId[2]);
			convertView.setTag(holder); 
		} else { 
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map = getData().get(position);
		String[] dataName = getDataName();
		holder.tips_leftimg.setBackgroundResource((Integer) map.get(dataName[0]));
		holder.tips_text.setText((String)map.get(dataName[1]));
		holder.tips_rightimg.setBackgroundResource((Integer) map.get(dataName[2]));
		return convertView;
	}

}
