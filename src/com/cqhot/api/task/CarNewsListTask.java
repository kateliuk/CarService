package com.cqhot.api.task;

import com.cqhot.api.data.CarNewsEntity;

public class CarNewsListTask extends BaseTask{
	private OnCarNewsListGettedListener mListener;
	public abstract interface OnCarNewsListGettedListener{
		public abstract void onCarNewsListGetted(CarNewsEntity.SubClass[] subclass);
	}
	public CarNewsListTask(OnCarNewsListGettedListener listener){
		this.mListener=listener;
	}
	@Override
	protected void onPostExecute(String result) {
		if(mListener!=null && result!=null) 
			mListener.onCarNewsListGetted(parseData(result,CarNewsEntity.class).getSubclass());
	}
	@Override
	protected String doInBackground(String... params) {
		String result=getApiInstance().getNewsInfo(params[0]);
		return result;
	}
}
