package com.umbrella.sg16drawabletest;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GPSActivity extends Activity {
	
	public static final String TAG = "GPSActivity";
	
	public static final String PROXIMITY_ALERT_ACTION = "com.umbrella.sg16drawabletest.receiver.action.PROXIMITY_ALERT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_gps);
		
		final LocationManager locationManager = (LocationManager)GPSActivity.this.getSystemService(Context.LOCATION_SERVICE);
		
		/**
		 * 获取我的坐标
		 */
		final TextView locationsTv = (TextView)this.findViewById(R.id.locations);
		Button myLocationBtn = (Button)this.findViewById(R.id.get_location);
		myLocationBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				List<String> locationProviders = locationManager.getProviders(true);
				
				StringBuilder res = new StringBuilder();
				
				for(String provider : locationProviders) {
					// 添加监听
					if(provider.equals(LocationManager.GPS_PROVIDER)) {
						locationManager.requestLocationUpdates(provider, 1000, 1, new MyLocationListener(locationsTv));
						
						Location location = locationManager.getLastKnownLocation(provider);
						String str = provider + ", latitude=" + location.getLatitude() + ", longitude=" + location.getLongitude();
						res.append(str + "\n");
						Log.i(TAG, str);						
					}
				}
				locationsTv.setText(res);
			}
		});
		
		/**
		 * 趋近告警
		 */
		Button startProximityAlertBtn = (Button)this.findViewById(R.id.start_proximity_alert);
		startProximityAlertBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				double latitude = 39.81999514;
				double longitude = 116.50065963;
				float radius = 2f;
				long expiration = -1;
				Intent intent = new Intent(PROXIMITY_ALERT_ACTION);
				PendingIntent pi = PendingIntent.getBroadcast(GPSActivity.this, -1, intent, 0);
//				try {
//					pi.send();
//				} catch (CanceledException e) {
//					e.printStackTrace();
//				}
				locationManager.addProximityAlert(latitude, longitude, radius, expiration, pi);
			}
		});
		
		/**
		 * Geocoder
		 */
		final Geocoder geocoder = new Geocoder(this);
		final EditText siteEt = (EditText)this.findViewById(R.id.site);
		Button getCoordinateBtn = (Button)this.findViewById(R.id.get_coordinate);
		final TextView addressTv = (TextView)this.findViewById(R.id.address);
		getCoordinateBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String site = siteEt.getText().toString();
				try {
					List<Address> addresses = geocoder.getFromLocationName(site, 10);
					if(addresses!=null && addresses.size()>0) {
						Address a = addresses.get(0);
						String addr = a.getCountryName()
								+ "," + a.getFeatureName()
								+ "," + a.getAdminArea() // 省
								+ "," + a.getSubAdminArea()
								+ "," + a.getLocality() // 
								+ "," + a.getSubLocality()
								+ "," + a.getThoroughfare() // 街
								+ "," + a.getSubThoroughfare()
								+ "," + a.getPremises()
								+ "," + a.getMaxAddressLineIndex()
								+ "," + a.getLatitude()
								+ "," + a.getLongitude()
								;
						Log.i(TAG, addr);
						addressTv.setText(addr);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	class MyLocationListener implements LocationListener {
		
		private TextView textView;
		
		public MyLocationListener(TextView textView) {
			super();
			this.textView = textView;
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			String msg = location.getProvider() + " changed! latitude=" + location.getLatitude() + ", longitude=" + location.getLongitude();
			Log.i(TAG, msg);
			this.textView.setText(msg);
		}
	}

}
