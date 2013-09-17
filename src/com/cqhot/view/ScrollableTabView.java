/*
 * Copyright (C) 2011 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cqhot.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cqhot.R;
import com.cqhot.activity.ListItemDetailActivity;
import com.cqhot.adapter.BasicAdapter;
import com.cqhot.adapter.NewsListAdapter;
import com.cqhot.adapter.TabAdapter;
import com.cqhot.adapter.TopAdapter;
import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.data.CarNewsEntity;
import com.cqhot.api.data.CarNewsEntity.SubClass;
import com.cqhot.api.data.TopListEntity;
import com.cqhot.api.data.TopListEntity.Top;
import com.cqhot.api.task.CarNewsListTask;
import com.cqhot.api.task.CarNewsListTask.OnCarNewsListGettedListener;
import com.cqhot.api.task.TopListTask;
import com.cqhot.api.task.TopListTask.OnTopListGettedListener;
import com.cqhot.api.util.ConnectionUtil;
import com.cqhot.api.util.FileRWUtil;


/**
 * I'm using a custom tab view in place of ActionBarTabs entirely for the theme
 * engine.
 */
@SuppressWarnings("deprecation")
public class ScrollableTabView extends HorizontalScrollView implements OnCarNewsListGettedListener,OnTopListGettedListener{

	private Activity activity;
    private Context mContext;

    private TabAdapter mAdapter;
    
    private BasicAdapter adapter;

    private final LinearLayout mContainer;

    private final ArrayList<View> mTabs = new ArrayList<View>();

    private Drawable mDividerDrawable;

    private final int mDividerColor = 0xFF636363;

    private int mDividerMarginTop = 12;

    private int mDividerMarginBottom = 12;

    private int mDividerWidth = 1;
    
    private String[] dataName={"id","title","content","link","image"};
	private int[] viewId={R.id.activity_item_img,R.id.activity_item_title,R.id.activity_item_memo};

	private ListView mListView1,mListView2;
	private List<Map<String,Object>> list;
	private Gallery mGallery1,mGallery2;
	private WebView webView;
	private int currentPage=0;
	private List<Map<String,Object>> cache_list1,cache_list2,cache_top1,cache_top2;
	private ProgressDialog dialog;	
	
    public ScrollableTabView(Context context) {
        this(context, null);
    }

    public ScrollableTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        this.mContext = context;

        mDividerMarginTop = (int)(getResources().getDisplayMetrics().density * mDividerMarginTop);
        mDividerMarginBottom = (int)(getResources().getDisplayMetrics().density * mDividerMarginBottom);
        mDividerWidth = (int)(getResources().getDisplayMetrics().density * mDividerWidth);

        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalFadingEdgeEnabled(false);

        mContainer = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(params);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);

        this.addView(mContainer);

    }

    public void setAdapter(TabAdapter adapter,List<Map<String,Object>> list,ListView mListView1,ListView mListView2,Gallery mGallery1,Gallery mGallery2,WebView webView,Activity activity) {
        this.mAdapter = adapter;
        this.list=list;
        this.mContext=activity.getApplicationContext();
        this.activity=activity;
        this.mListView1=mListView1;
        this.mListView2=mListView2;
        this.mGallery1=mGallery1;
        this.mGallery2=mGallery2;
        this.webView=webView;
        mListView1.setOnItemClickListener(itemClick);
        mListView2.setOnItemClickListener(itemClick);
        if (mAdapter != null)
            initTabs();
    }
    /**
     * 
     */
    private void initTabs() {

        mContainer.removeAllViews();
        mTabs.clear();

        if (mAdapter == null)
            return;

        for (int i = 0; i <3; i++) {

            final int index = i;

            View tab = mAdapter.getView(i);
            mContainer.addView(tab);
            tab.setFocusable(true);

            mTabs.add(tab);

            if (i !=list.size()) {
                mContainer.addView(getSeparator());
            }
            if(index==0){
            	executeTask(list,index);
            	selectTab(index);
            }
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                		executeTask(list,index);
                        selectTab(index);
                }
            });
        }
    }
    @SuppressWarnings("unchecked")
	private void executeTask(List<Map<String,Object>> list,int index) {
    	dialog= new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("加载中，请稍等......");
        dialog.show();
    	if(list.size()>0){
	    		if(index==0){
	    			currentPage=1;
    				mListView1.setVisibility(View.VISIBLE);
    				mListView2.setVisibility(View.GONE);
    				webView.setVisibility(View.GONE);
    				webView.stopLoading();
    				cache_list1=FileRWUtil.readObjFromFile(CarUrl.SUCLASS_FIRST_LIST);
    				cache_top1=FileRWUtil.readObjFromFile(CarUrl.TOP_FIRST_LIST);
    				initData(list,cache_list1,cache_top1,index,mListView1,mGallery1);
    			}else if(index==1){
    				currentPage=2;
    				mListView1.setVisibility(View.GONE);
    				mListView2.setVisibility(View.VISIBLE);
    				webView.setVisibility(View.GONE);
    				webView.stopLoading();
    				cache_list2=FileRWUtil.readObjFromFile(CarUrl.SUCLASS_SECOND_LIST);
    				cache_top2=FileRWUtil.readObjFromFile(CarUrl.TOP_SECOND_LIST);
    				initData(list,cache_list2,cache_top2,index,mListView2,mGallery2);
    			}else if(index==2){
    				webView.loadUrl(CarUrl.CAR_SINA_WEIBO);
    				webView.setVisibility(View.VISIBLE);
    				mListView1.setVisibility(View.GONE);
    				mListView2.setVisibility(View.GONE);
    			}
    	}
    	dialog.dismiss();
	}
    private void initData(List<Map<String,Object>> list,List<Map<String,Object>> cache_list,final List<Map<String,Object>> cache_top,int index,ListView mListView,Gallery mGallery){
    	if(ConnectionUtil.isConnet(mContext)){
			if(cache_list.size()>0){
				adapter=new NewsListAdapter(mContext, R.layout.activity_item, viewId, cache_list, dataName);
				mListView.setAdapter(adapter);
			}else{
				new CarNewsListTask(this).execute((String)list.get(index).get("link"));
			}
			if(cache_top.size()>0){
				mGallery.setAdapter(new TopAdapter(mContext,activity,cache_top));
			}else{
				new TopListTask(this).execute((String)list.get(index).get("typeid"));
			}
		}else{
			adapter=new NewsListAdapter(mContext, R.layout.activity_item, viewId, cache_list, dataName);
			mListView.setAdapter(adapter);
			mGallery.setAdapter(new TopAdapter(mContext,activity,cache_top));
		}
    }
    @Override
	public void onCarNewsListGetted(SubClass[] subclass) {
    	if (subclass != null) {
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (CarNewsEntity.SubClass s :subclass) {
				if (s != null) {
					Map<String, Object> map = new  HashMap<String, Object>();
					map.put(dataName[0],s.id==null?"":s.id);
					map.put(dataName[1],s.title==null?"":s.title);
					map.put(dataName[2],s.content==null?"":s.content);
					map.put(dataName[3],s.link==null?"":s.link);
					map.put(dataName[4],s.image==null?"":s.image);
					list.add(map);
				}
			}
			adapter=new NewsListAdapter(mContext, R.layout.activity_item, viewId, list, dataName);
			if(currentPage==1){
				mListView1.setAdapter(adapter);
				FileRWUtil.writeObjToFile(list,CarUrl.SUCLASS_FIRST_LIST);
			}else if(currentPage==2){
				mListView2.setAdapter(adapter);
				FileRWUtil.writeObjToFile(list,CarUrl.SUCLASS_SECOND_LIST);
			}
		}
	}
    @Override
	public void onTopListGetted(Top[] top) {
    	final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (TopListEntity.Top t :top) {
			if (t != null) {
				Map<String, Object> map = new  HashMap<String, Object>();
				map.put(dataName[0],t.id==null?"":t.id);
				map.put(dataName[1],t.title==null?"":t.title);
				map.put(dataName[3],t.link==null?"":t.link);
				map.put(dataName[4],t.image==null?"":t.image);
				list.add(map);
			}
		}
		if(currentPage==1){
			mGallery1.setAdapter(new TopAdapter(mContext,activity,list));
			FileRWUtil.writeObjToFile(list,CarUrl.TOP_FIRST_LIST);
		}else if(currentPage==2){
			mGallery2.setAdapter(new TopAdapter(mContext,activity,list));
			FileRWUtil.writeObjToFile(list,CarUrl.TOP_SECOND_LIST);
		}
	}

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        if (changed)
//            selectTab(mPager.getCurrentItem());
    }

	private View getSeparator() {
        View v = new View(mContext);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDividerWidth,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, mDividerMarginTop, 0, mDividerMarginBottom);
        v.setLayoutParams(params);

        if (mDividerDrawable != null)
            v.setBackgroundDrawable(mDividerDrawable);
        else
            v.setBackgroundColor(mDividerColor);

        return v;
    }

    private void selectTab(int position) {

        for (int i = 0, pos = 0; i < mContainer.getChildCount(); i += 2, pos++) {
            View tab = mContainer.getChildAt(i);
            tab.setSelected(pos == position);
        }

        View selectedTab = mContainer.getChildAt(position * 2);

        final int w = selectedTab.getMeasuredWidth();
        final int l = selectedTab.getLeft();

        final int x = l - this.getWidth() / 2 + w / 2;

        smoothScrollTo(x, this.getScrollY());

    }
    private OnItemClickListener itemClick=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) adapter.getItem(position-1);
			Intent intent= new Intent();  
			intent.putExtra(dataName[0],(String)map.get(dataName[0]));
			intent.putExtra(dataName[1],(String)map.get(dataName[1]));
			intent.putExtra(dataName[3],(String)map.get(dataName[3]));
			intent.setClass(activity,ListItemDetailActivity.class);
			activity.startActivity(intent);
		}
	};




}
