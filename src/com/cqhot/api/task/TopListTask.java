package com.cqhot.api.task;

import com.cqhot.api.data.TopListEntity;

public class TopListTask extends BaseTask{
	private OnTopListGettedListener mListener;
	public abstract interface OnTopListGettedListener{
		public abstract void onTopListGetted(TopListEntity.Top[] top);
	}
	public TopListTask(OnTopListGettedListener listener){
		this.mListener=listener;
	}
	@Override
	protected void onPostExecute(String result) {
		if(mListener!=null && result!=null) 
			mListener.onTopListGetted(parseData(result,TopListEntity.class).getTop());
	}
	@Override
	protected String doInBackground(String... params) {
		String result=getApiInstance().getTopinfo(params[0]);
		return result;
	}
}
