package com.cqhot.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cqhot.BaiduMapApplication;
import com.cqhot.R;

@SuppressLint("HandlerLeak")
public class LocationOverlay extends Activity {

	static MapView mMapView = null;

	private MapController mMapController = null;
	public MKMapViewListener mMapListener = null;
	FrameLayout mMapViewContainer = null;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public NotifyLister mNotifyer = null;
//	OverlayTest ov = null;
	Button testUpdateButton = null;
	Button testItemButton = null;
	Button removeItemButton = null;
	Button removeAllItemButton = null;
	private String address;

	// 存放overlayitem
	public List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	EditText indexText = null;
	MyLocationOverlay myLocationOverlay = null;
	int index = 0;
	/**
	 * 圆心经纬度坐标,这里是地图显示坐标
	 */
	private static int cLat = 50334502;
	private static int cLon = 120274922;
	LocationData locData = null;
	// 存放overlay图片
	public List<Drawable> res = new ArrayList<Drawable>();

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(LocationOverlay.this, "msg:" + msg.getData().getString("address"),
					Toast.LENGTH_SHORT).show();
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BaiduMapApplication app = (BaiduMapApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(BaiduMapApplication.strKey,
					new BaiduMapApplication.MyGeneralListener());
		}
		setContentView(R.layout.activity_locationoverlay);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapController.setZoom(14);
		mMapController.enableClick(true);
		mMapView.setBuiltInZoomControls(true);
//		ov = new OverlayTest(marker, this, mMapView);
		initMapView();
		mMapListener = new MKMapViewListener() {

			@Override
			public void onMapMoveFinish() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				// TODO Auto-generated method stub
				String title = "";
				if (mapPoiInfo != null) {
					title = mapPoiInfo.strText;
					Toast.makeText(LocationOverlay.this, title,
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub
			}
		};
		mLocClient = new LocationClient(LocationOverlay.this);
		mLocClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		option.setAddrType("all");
		option.setLocationNotify(false);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mMapView.regMapViewListener(BaiduMapApplication.getInstance().mBMapManager,mMapListener);
		myLocationOverlay = new MyLocationOverlay(mMapView);
		locData = new LocationData();
		myLocationOverlay.setData(locData);
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
	}

	/**
	 * view转bitmap
	 * @param view
	 * @return
	 */
		public Bitmap convertViewToBitmap(View view) {
			view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
			view.buildDrawingCache();
			Bitmap bitmap = view.getDrawingCache();

			return bitmap;
		}


	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mLocClient != null){
			mLocClient.stop();
		}
		mMapView.destroy();
		BaiduMapApplication app = (BaiduMapApplication) this.getApplication();
		if (app.mBMapManager != null) {
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	private void initMapView() {
		mMapView.setLongClickable(true);
//		 mMapController.setMapClickEnable(true);
//		 mMapView.setSatellite(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		private static final String TAG = "MyLocationListenner";

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
				locData.latitude = location.getLatitude();
				locData.longitude = location.getLongitude();
				locData.accuracy = location.getRadius();
				locData.direction = location.getDerect();
				System.out.println(locData.latitude+"%%%%%%%%%%%%%%%%%"+locData.longitude);
				cLat = (int) (location.getLatitude() * 1E6);
				cLon = (int) (location.getLongitude() * 1E6);
				address=  location.getAddrStr();
				Log.i(TAG, location.getAddrStr() + location.getLatitude()
						+ "---" + location.getLongitude());
				myLocationOverlay.setData(locData);
				mMapView.refresh();
				Message message = new Message();
				Bundle data = new Bundle();
				data.putString("address", address);
				message.setData(data);

				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)), message);
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
		}
	}


	@SuppressWarnings("rawtypes")
	class OverItemT extends ItemizedOverlay {
		
		PopupOverlay popo = null;
		
		Toast mToast = null;
		
		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		
		public OverItemT(Drawable marker, final Context context, GeoPoint pt, String title,MapView mMapView) {
			super(marker,mMapView);
			OverlayItem item = new OverlayItem(pt, title, null);
			mGeoList.add(item);
			popo = new PopupOverlay( mMapView,new PopupClickListener() {
				
				@Override
				public void onClickedPopup(int index) {
//				if(index==1){
//					Intent intent = new Intent();
//					intent.setClass(context, CenterInfoActivity.class);
//				}
				}
			});
			addItem(item);
			//populate();
		}
		protected boolean onTap(int index) {
		Bitmap[] bmps = new Bitmap[3];
			LayoutInflater inflater;//定义布局过滤器
			Bitmap popShow;//图片
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.balloon_overlay,null);
			TextView tv2 = (TextView) layout.findViewById(R.id.balloon_item_snippet);
			tv2.setText("华美整形医院\n重庆市渝中区上清寺路7号");
			popShow = convertViewToBitmap(layout);//将xml布局转化为view类
			
			bmps[0] = popShow;
			
			
			popo.showPopup(bmps, getItem(index).getPoint(), 32);
			return true;
		}
		public boolean onTap(GeoPoint pt, MapView mapView){
			if (popo != null){
				popo.hidePop();
			}
			super.onTap(pt,mapView);
			return false;
		}
		
	}
}

