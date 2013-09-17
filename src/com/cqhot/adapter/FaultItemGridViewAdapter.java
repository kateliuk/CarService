package com.cqhot.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FaultItemGridViewAdapter extends BasicAdapter{
	public FaultItemGridViewAdapter(Context context, int view, int[] viewId,
			List<Map<String, Object>> data, String[] dataName) {
		super(LayoutInflater.from(context), view, viewId, data, dataName);
	}
	class ViewHolder {
		public ImageButton image;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			int[] viewId = getViewId();
			convertView = getInflater().inflate(getView(),null);
			holder.image=(ImageButton)convertView.findViewById(viewId[0]);
			convertView.setTag(holder); 
		} else { 
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map = getData().get(position);
		String[] dataName = getDataName();
		holder.image.setBackgroundResource((Integer) map.get(dataName[0]));
		return convertView;
	}
}
