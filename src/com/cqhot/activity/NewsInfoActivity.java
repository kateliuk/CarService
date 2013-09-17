package com.cqhot.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.cqhot.R;
import com.cqhot.adapter.ScrollingTabsAdapter;
import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.util.FileRWUtil;
import com.cqhot.view.ScrollableTabView;


public class NewsInfoActivity extends FragmentActivity{
	private List<Map<String,Object>> list;
	private List<String> types=new ArrayList<String>();
	private ListView mListView1,mListView2;
	private View mHeader1,mHeader2;
	private Gallery mGallery1,mGallery2;
	private WebView webView;
	private ProgressBar mLoadProgress;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_info);
		list=FileRWUtil.readObjFromFile(CarUrl.CACHE_TYPE);
		initPager();
	}
	@SuppressLint("SetJavaScriptEnabled")
	public void initPager() {
    	mListView1=(ListView)findViewById(R.id.listview1);
    	mListView2=(ListView)findViewById(R.id.listview2);
    	LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
    	mHeader1=inflater.inflate(R.layout.list_head,null);
    	mGallery1 =(Gallery) mHeader1.findViewById(R.id.gallery_news1);
    	mHeader2=inflater.inflate(R.layout.list_head_second,null);
    	mGallery2 = (Gallery) mHeader2.findViewById(R.id.gallery_news2);
    	mGallery2.setVerticalScrollBarEnabled(false);
    	mListView1.addHeaderView(mHeader1);//添加ListView列表头
//    	mListView1.setOnItemClickListener(this);
    	mListView2.addHeaderView(mHeader2);//添加ListView列表头
//    	mListView2.setOnItemClickListener(this);
    	webView=(WebView) findViewById(R.id.sina_weibo); 
    	webView.removeAllViews();
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.setWebViewClient(new WebClient());
        mLoadProgress=(ProgressBar)findViewById(R.id.contact_progressbar_loading);
    	if(list.size()>0){
	        for(int i=0;i<list.size();i++){
	        	types.add((String) list.get(i).get("type"));
	        }
    	}
        initScrollableTabs();
        
    }
    class WebClient extends WebViewClient{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			mLoadProgress.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			mLoadProgress.setVisibility(View.GONE);
			super.onPageFinished(view, url);
		}
	}
    public void initScrollableTabs() {
    	
        ScrollableTabView mScrollingTabs = (ScrollableTabView)findViewById(R.id.scrollingTabs);
        types.add("新浪微博");
        ScrollingTabsAdapter mScrollingTabsAdapter = new ScrollingTabsAdapter(this,types);
        mScrollingTabs.setAdapter(mScrollingTabsAdapter,list,mListView1,mListView2,mGallery1,mGallery2,webView,NewsInfoActivity.this);
    }
    public void appAbout(View view){
    	Intent intent = new Intent(this,AboutAcivity.class);
    	startActivity(intent);
    }
}
