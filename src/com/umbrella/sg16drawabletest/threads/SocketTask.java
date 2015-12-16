package com.umbrella.sg16drawabletest.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.util.Log;

import com.umbrella.sg16drawabletest.MainActivity;
/**
 * Socket≤‚ ‘
 * @author ¥Û÷ﬁ
 *
 */
public class SocketTask implements Runnable {

	@Override
	public void run() {
		Socket socket = null;
		try {
			socket = new Socket("192.168.0.104", 8888);
			InputStream is = socket.getInputStream();
			byte[] buf = new byte[1024];
			int i=-1;
			StringBuilder res = new StringBuilder();
			while((i=is.read(buf))!=-1) {
				res.append(new String(buf, 0, i));
			}
			Log.i(MainActivity.TAG, "Server:" + res.toString());
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "“Ï≥£¡À", e);
		} finally {
			try {
				if(socket!=null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
