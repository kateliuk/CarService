package com.cqhot.api;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.util.NetworkUtil;

public class CQNApi {
	private static CQNApi mInstance;
	
	private CQNApi(){
	}
	public static CQNApi getInstance(){
		if(mInstance==null){
			mInstance=new CQNApi();
		}
		return mInstance;
	}
	public String getTopinfo(String url){
		return doUrlConnect(CarUrl.TOP_PATH+url);
	}
	public String getCarTypeinfo(String url){
		return doUrlConnect(url);
	}
	public String getNewsInfo(String url){
		return doUrlConnect(url);
	}
	public String doUrlConnect(String url){
//		String uri=url+CQNUrl.NEWS_INFO;
		String result=null;
		try {
			result=NetworkUtil.openUri(url,null);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
