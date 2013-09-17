package com.cqhot.api.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Environment;

public class FileRWUtil {
	public static final String cache_path = "CarService";

	private static FileRWUtil download = null;
	public boolean isdownload = false;

	public synchronized static FileRWUtil getInstance() {

		if (download == null) {
			download = new FileRWUtil();
		}
		return download;
	}

	/**
	 * 创建文件夹 存储缓存文件
	 * 
	 * @param name
	 * @return
	 */
	public static String cerateFile(String name) {
		/** 检测SD卡是否存在 */
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}

		String path = Environment.getExternalStorageDirectory() + "/"
				+ cache_path + "/" + name;

		/**
		 * 判断SD卡上的文件夹是否存在 不存在的情况 创建文件夹
		 */
		if (new File(path).exists() == false) {
			new File(path).mkdirs();
		}
		return path;
	}
	@SuppressWarnings("rawtypes") 
	public static void writeObjToFile(List rsls,String filename){
		String path=cerateFile(filename);
		  //List的map序列化操作
		  try{
		        ByteArrayOutputStream baos=new ByteArrayOutputStream();   
		        ObjectOutputStream oos = new ObjectOutputStream(baos); //此类将对像写入字节流   
				Iterator item=rsls.iterator();   
		        while(item.hasNext()){   
		            Object obj=item.next();   
		            oos.writeObject(obj);   
		        }
		        byte[] data = baos.toByteArray();//獲取對像的序列化數據   
		        OutputStream os = new   FileOutputStream(new File(path+".bin"));//System.out;   
		        os.write(data);   
		        os.flush();   
		        os.close();  
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		 }
		 
		 /**
		  * 从序列化文件中读取、并对象化
		  * @param filename
		  * @return
		  */
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		public static List readObjFromFile(String filename){
			 String path=cerateFile(filename);
		  List rsls = new ArrayList ();
		  try{
		  FileInputStream fis = new FileInputStream(path+".bin");
		  ObjectInputStream ois=new ObjectInputStream(fis);
		  while(fis.available()>0)
		  {
		   rsls.add(ois.readObject()); 
		  }
		  ois.close();
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  return rsls;
		 }
		
}
