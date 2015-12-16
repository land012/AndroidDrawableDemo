package com.umbrella.sg16drawabletest.handlers;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.umbrella.sg16drawabletest.MainActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class URLHandler extends Handler {

	@Override
	public void handleMessage(Message msg) {
		try {
			URL url = new URL("http://192.168.0.104:9999/springmvc/hello");
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(false);
			InputStream is = conn.getInputStream();
			byte[] buf = new byte[1024];
			StringBuilder res = new StringBuilder();
			int i = -1;
			while((i=is.read(buf))!=-1) {
				res.append(new String(buf, 0, i));
			}
			Log.i(MainActivity.TAG, res.toString());
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "URL“Ï≥£¡À", e);
		}
	}
	
}
