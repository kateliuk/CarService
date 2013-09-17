package com.cqhot.api.task;

import com.cqhot.api.data.CarTypeEntity;

public class CarTypeTask extends BaseTask{
	private OnCarTypeGettedListener mListener;
	public abstract interface OnCarTypeGettedListener{
		public abstract void onCarTypeGetted(CarTypeEntity.Type[] type);
	}
	public CarTypeTask(OnCarTypeGettedListener listener){
		this.mListener=listener;
	}
	@Override
	protected void onPostExecute(String result) {
		if(mListener!=null && result!=null) 
			mListener.onCarTypeGetted(parseData(result,CarTypeEntity.class).getType());
	}
	@Override
	protected String doInBackground(String... params) {
		String result=getApiInstance().getCarTypeinfo(params[0]);
		return result;
	}
}
