package com.cqhot.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cqhot.R;
import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.util.AsynImageLoader;

@SuppressWarnings("deprecation")
public class TopAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Activity mActivity;
	private List<Map<String,Object>> list;
	
	public TopAdapter(Context context,Activity activity,List<Map<String,Object>> list){
		this.inflater=LayoutInflater.from(context);
		this.mActivity=activity;
		this.list=list;
	}
	
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		ImageView iv = new ImageView (mActivity);
		LinearLayout gallery_layout=(LinearLayout) inflater.inflate(R.layout.gallery,null);
		ImageView iv=(ImageView) gallery_layout.findViewById(R.id.img_gallery);
		String url=CarUrl.CAR_IMAGEURL_START+(String) list.get(position).get("image");
		new AsynImageLoader().showImageAsyn(iv, url.toString(), R.drawable.default_img);
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		iv.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,350));
		return iv;
	}

}
