package com.cqhot.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqhot.R;
import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.util.AsynImageLoader;

public class NewsListAdapter extends BasicAdapter{
	private AsynImageLoader asynImageLoader;
	private ViewHolder holder;
	public NewsListAdapter(Context context, int view, int[] viewId,
			List<Map<String, Object>> data, String[] dataName) {
		super(LayoutInflater.from(context), view, viewId, data, dataName);
	}
	class ViewHolder{
		public TextView title,memo;
		public ImageView img;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup views) {
		asynImageLoader = new AsynImageLoader();
		if(convertView==null){
			holder=new ViewHolder();
			int[] viewId=getViewId();
			convertView=getInflater().inflate(getView(), null);
			holder.img=(ImageView) convertView.findViewById(viewId[0]);
			holder.title=(TextView) convertView.findViewById(viewId[1]);
			holder.memo=(TextView) convertView.findViewById(viewId[2]);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Map<String,Object> map=getData().get(position);
		String[] dataName=getDataName();
//		mImageDownloader.download(CarUrl.CAR_IMAGEURL_START+(String)map.get(dataName[4]), holder.img, ImageDownloader.Type.NORMAL);
		System.out.println(map.get(dataName[4])+"<<<<<<<<<<<<<<<<<<<<<<");
		if(map.get(dataName[4])!=null||map.get(dataName[4])!=""){
			asynImageLoader.showImageAsyn(holder.img, CarUrl.CAR_IMAGEURL_START+map.get(dataName[4]),R.drawable.default_img);
		}else{
			holder.img.setBackgroundResource(R.drawable.detail_photo_default_back);
		}
		holder.title.setText((String) map.get(dataName[1]));
		holder.memo.setText((String) map.get(dataName[2]));
		return convertView;
	}

}
