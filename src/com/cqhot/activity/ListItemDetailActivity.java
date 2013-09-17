package com.cqhot.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cqhot.R;

public class ListItemDetailActivity extends Activity{
	@SuppressWarnings("unused")
	private String id,title,link;
	private ProgressBar mProgressBar;
	private TextView detail_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.list_item_detail);
		id=getIntent().getStringExtra("id");
		title=getIntent().getStringExtra("title");
		link=getIntent().getStringExtra("link");
		this.initUI();
	}
	@SuppressLint("SetJavaScriptEnabled")
	private void initUI() {
		detail_title=(TextView) findViewById(R.id.detail_title);
		detail_title.setText(title);
		WebView webView=(WebView)findViewById(R.id.news_detail_web);
		webView.removeAllViews();
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.setWebViewClient(new WebClient());
        webView.loadUrl(link);
        mProgressBar=(ProgressBar)findViewById(R.id.news_detail_progressbar_loading);
	}
	class WebClient extends WebViewClient{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			mProgressBar.setVisibility(View.GONE);
			super.onPageFinished(view, url);
		}
		
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
