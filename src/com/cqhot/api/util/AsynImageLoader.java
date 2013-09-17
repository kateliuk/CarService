package com.cqhot.api.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cqhot.api.contant.CarUrl;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

@SuppressLint("HandlerLeak")
public class AsynImageLoader {

	private Map<String, SoftReference<Bitmap>> caches;
	private List<Task> taskQueue;
	private boolean isRunning = false;

	public AsynImageLoader() {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<AsynImageLoader.Task>();
		isRunning = true;
		new Thread(runnable).start();
	}

	public void showImageAsyn(ImageView imageView, String url, int resId) {
		imageView.setTag(url);
		Bitmap bitmap = loadImageAsyn(url, getImageCallback(imageView, resId));
		if (bitmap == null) {
			//imageView.setImageResource(resId);
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	private ImageCallback getImageCallback(final ImageView imageView,final int resId) {
		return new ImageCallback() {
			@Override
			public void loadImage(String path, Bitmap bitmap) {
				if (path.equals(imageView.getTag().toString()) && bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView.setImageResource(resId);
				}
			}
		};
	}
	
	
//	PUBLIC VOID SHOWIMAGEASYN(IMAGEVIEW IMAGEVIEW, INT RESID,MAP<STRING, OBJECT> MAP) {
//		
//		IMAGEVIEW.SETTAG(MAP);
//		BITMAP BITMAP = LOADIMAGEASYN(MAP.GET(CONSTANT.DATANAME_NEWSLIST[0]).TOSTRING(), GETIMAGECALLBACK(IMAGEVIEW, RESID,MAP));
//		IF (BITMAP == NULL) {
//			IMAGEVIEW.SETIMAGERESOURCE(RESID);
//		} ELSE {
//			IMAGEVIEW.SETIMAGEBITMAP(BITMAP);
//		}
//	}

//	private ImageCallback getImageCallback(final ImageView imageView,final int resId,final Map<String, Object> map) {
//		return new ImageCallback() {
//			@Override
//			public void loadImage(String path, Bitmap bitmap) {
//				if (path.equals(map.get(Constant.dataName_newslist[0]).toString()) && bitmap != null) {
//					imageView.setImageBitmap(bitmap);
//				} else {
//					imageView.setImageResource(resId);
//				}
//			}
//		};
//	}
	
	public Bitmap loadImageAsyn(String path, ImageCallback callback) {
		if (caches.containsKey(path)) {
			SoftReference<Bitmap> rf = caches.get(path);
			Bitmap bitmap = rf.get();
			if (bitmap == null) {
				caches.remove(path);
			} else {
				return bitmap;
			}
		} else {
			Task task = new Task();
			task.path = path;
			task.callback = callback;
			if (!taskQueue.contains(task)) {
				taskQueue.add(task);
				synchronized (runnable) {
					runnable.notify();
				}
			}
		}
		return null;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Task task = (Task) msg.obj;
			task.callback.loadImage(task.path, task.bitmap);
		}

	};

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			while (isRunning) {
				while (taskQueue.size() > 0) {
					Task task = taskQueue.remove(0);
					task.bitmap = getbitmapAndwrite(task.path);
					caches.put(task.path,new SoftReference<Bitmap>(task.bitmap));
					if (handler != null) {
						Message msg = handler.obtainMessage();
						msg.obj = task;
						handler.sendMessage(msg);
					}
				}
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};

	public interface ImageCallback {
		void loadImage(String path, Bitmap bitmap);
	}

	class Task {
		String path;
		Bitmap bitmap;
		ImageCallback callback;

		@Override
		public boolean equals(Object o) {
			Task task = (Task) o;
			return task.path.equals(path);
		}
	}

	public static Bitmap getbitmapAndwrite(String imageUri) {
		Bitmap bitmap = null;
		bitmap = getBitmapByPath(imageUri);
		if (bitmap == null) {
			try {
				URL myFileUrl = new URL(imageUri);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				File cacheFile = getCacheFile(imageUri);
				BufferedOutputStream bos = null;
				bos = new BufferedOutputStream(new FileOutputStream(cacheFile));
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = is.read(buf)) > 0) {
					bos.write(buf, 0, len);
				}
				is.close();
				bos.close();
				bitmap = BitmapFactory.decodeFile(cacheFile.getCanonicalPath());
 
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		} else {
			return bitmap;
		}
	}

	public static File getCacheFile(String imageUri) {
		File cacheFile = null;
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String fileName = getFileName(imageUri);
				File dir = new File(CarUrl.IMAGES);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheFile;
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

	public static synchronized Bitmap getBitmapByPath(String url) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			int index=url.length();
			String imgName=url.substring(index-4);
			if(imgName.equals(".jpg")){
				String image_name = getFileName(url);
				String path_ = CarUrl.IMAGES+ image_name;
				File mfile = new File(path_);
				if (mfile.exists()) {
					return BitmapFactory.decodeFile(path_);
				
			}else{
				return null;
			}
			} else
				return null;
		} else
			return null;
	}
}
