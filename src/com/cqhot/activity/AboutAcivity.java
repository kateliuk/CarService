package com.cqhot.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cqhot.R;

public class AboutAcivity extends Activity {
	private TextView detail_title, company_name, company_address, company_tel,
			company_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_about);
		initUI();
	}

	private void initUI() {
		detail_title = (TextView) findViewById(R.id.detail_title);
		company_name = (TextView) findViewById(R.id.company_name);
		company_address = (TextView) findViewById(R.id.company_address);
		company_tel = (TextView) findViewById(R.id.company_tel);
		company_web = (TextView) findViewById(R.id.company_web);
		detail_title.setText("广本长捷dsg");
		company_name.setText("济南搜索在线广告有限公司");
		company_address.setText("济南市二环东路3966号东环国际广场B座 2305室");
		company_tel.setText("400 655 2477");
		company_web.setText("http://www.s-online.cn/" + " （点击打开网页）");
	}

	public void cellTel(View view) {
		call_dialog("400 655 2477");
	}

	public void clickWeb(View view) {
		Intent intent = new Intent(this, CompanyActivity.class);
		intent.putExtra("web", "http://www.s-online.cn/");
		intent.putExtra("name", "济南搜索在线广告有限公司");
		startActivity(intent);
	}

	private void call_dialog(final String num) {
		AlertDialog.Builder builder = new AlertDialog.Builder(AboutAcivity.this);
		builder.setTitle("电话号码：");
		builder.setMessage(num);
		builder.setNeutralButton("拨打", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ num));
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}
}
