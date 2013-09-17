package com.cqhot.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.cqhot.R;
import com.cqhot.api.contant.CarUrl;
import com.cqhot.api.data.CarTypeEntity;
import com.cqhot.api.data.CarTypeEntity.Type;
import com.cqhot.api.task.CarTypeTask;
import com.cqhot.api.task.CarTypeTask.OnCarTypeGettedListener;
import com.cqhot.api.util.FileRWUtil;

public class WelcomeActivity extends Activity implements OnCompletionListener,OnCarTypeGettedListener{
	private VideoView videoView;
	private String[] dataName={"typeid","type","link"};
	@Override
     public void onCreate (Bundle savedInstanceState) {
         super. onCreate (savedInstanceState);
         setContentView(R.layout.welcome);
         new CarTypeTask(this).execute(CarUrl.TYPE_URL);
         initUI();
     }
	@SuppressLint("SdCardPath")
	private void initUI(){
		videoView=(VideoView) findViewById(R.id.videoView);
		videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.loading));
		videoView.start();
		videoView.setOnCompletionListener(this);
	}
	@Override
	public void onCompletion(MediaPlayer videoView) {
		Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public void onCarTypeGetted(Type[] types) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (types.length>0) {
			for (CarTypeEntity.Type type :types) {
				if (type != null) {
					Map<String, Object> map = new  HashMap<String, Object>();
					map.put(dataName[0],type.typeid==null?"":type.typeid);
					map.put(dataName[1],type.type==null?"":type.type);
					map.put(dataName[2],type.link==null?"":type.link);
					list.add(map);
				}
			}
//			@SuppressWarnings("rawtypes")
//			List cacheList=FileRWUtil.readObjFromFile(CarUrl.CACHE_TYPE);
//			if(cacheList==null||cacheList.size()<=0){
				FileRWUtil.writeObjToFile(list,CarUrl.CACHE_TYPE);
//			}
		}
	}
	
}
