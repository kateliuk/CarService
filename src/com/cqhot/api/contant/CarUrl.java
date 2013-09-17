package com.cqhot.api.contant;

import android.os.Environment;

public class CarUrl {
	public static final String APP_NAME="CarService";
	public static final String TYPE_URL="http://test001.cqhot.com/demo/type.php";
	public static final String CAR_IMAGEURL_START="http://test001.cqhot.com/demo/images/";
	public static final String TOP_PATH="http://test001.cqhot.com/demo/newstop.php?typeid=";
	public static final String CAR_SINA_WEIBO="http://e.weibo.com/u/1964093071";
	
	public static final String CACHE_TYPE="type/type";
	public static final String SUCLASS_FIRST_LIST="subclass/first_list";
	public static final String SUCLASS_SECOND_LIST="subclass/second_list";
	
	public static final String TOP_FIRST_LIST="top/first_top";
	public static final String TOP_SECOND_LIST="top/second_top";
	
	public static final String SD_CARD=Environment.getExternalStorageDirectory().getPath()+"/";
	public static final String IMAGES=SD_CARD+APP_NAME+"/images/";

}
