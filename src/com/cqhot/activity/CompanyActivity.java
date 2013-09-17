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

public class CompanyActivity extends Activity{
	private String web,name;
	private TextView detail_title;
	private WebView webView;
	private ProgressBar progressbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_web);
		web=getIntent().getStringExtra("web");
		name=getIntent().getStringExtra("name");
		initUI();
	}
	@SuppressLint("SetJavaScriptEnabled")
	private void initUI(){
		detail_title=(TextView) findViewById(R.id.detail_title);
		webView=(WebView) findViewById(R.id.detail_webview);
		progressbar=(ProgressBar) findViewById(R.id.load_progressbar);
		
		detail_title.setText(name);
		webView.removeAllViews();
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.setWebViewClient(new WebClient());
        webView.loadUrl(web);
	}
	class WebClient extends WebViewClient{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progressbar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			progressbar.setVisibility(View.GONE);
			super.onPageFinished(view, url);
		}
	}
}
