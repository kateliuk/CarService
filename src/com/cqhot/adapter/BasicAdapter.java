package com.cqhot.adapter;

import java.util.List; 
import java.util.Map;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BasicAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private int mView;
	private int[] mViewId;
	private String[] mDataName;
	private List<Map<String, Object>> mData;

	public BasicAdapter(LayoutInflater inflater, int view, int[] viewId, List<Map<String, Object>> data, String[] dataName) {
		this.mInflater = inflater;
		this.mView = view;
		this.mViewId = viewId;
		this.mData = data;
		this.mDataName = dataName;
	}

	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}


	public long getItemId(int position) {
		return mData.get(position).hashCode();
	}

	public void addExtraData(List<Map<String, Object>> data) {
		for (int i = 0; i < data.size(); i++)
			mData.add(data.get(i));
	}

	public void clearData() {
		mData.clear();
	}

	protected LayoutInflater getInflater() {
		return mInflater;
	}

	protected int getView() {
		return mView;
	}

	protected int[] getViewId() {
		return mViewId;
	}

	protected List<Map<String, Object>> getData() {
		return mData;
	}

	protected String[] getDataName() {
		return mDataName;
	}
}
