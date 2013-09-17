package com.cqhot.api.task;

import android.os.AsyncTask;

import com.cqhot.api.CQNApi;
import com.google.gson.Gson;

public abstract class BaseTask extends AsyncTask<String,Void, String>{
	
	protected CQNApi getApiInstance(){
		return CQNApi.getInstance();
	}
	protected <T> T parseData(String data,Class<T> pkg){
		Gson gson=new Gson();
		return gson.fromJson(data,pkg);
	}
}
