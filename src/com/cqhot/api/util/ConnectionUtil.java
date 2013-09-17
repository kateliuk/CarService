package com.cqhot.api.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtil {
	
	public static boolean isConnet(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo info = manager.getActiveNetworkInfo();  
		if(info!=null && info.isConnected()){  
		    return true;  
		}else{  
		    return false;  
		}  
	}
}
