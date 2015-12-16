package com.umbrella.sg16drawabletest.receiver;

import com.umbrella.sg16drawabletest.GPSActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class ProximityAlertReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(GPSActivity.TAG, "I am ProximityAlertReceiver");
		if(intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false)) {
			Toast.makeText(context, "注意！！进入目标区域！", Toast.LENGTH_LONG).show();
		}
	}

}
