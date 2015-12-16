package com.umbrella.sg16drawabletest.asynctasks;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.umbrella.sg16drawabletest.MainActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class WeatcherHttpAsyncTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.i(MainActivity.TAG, "getWeather, theCityCode=" + params[0]);
			
			String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
			
//			HttpPost request = new HttpPost(url);
//			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			list.add(new BasicNameValuePair("theCityCode", params[0]));
//			list.add(new BasicNameValuePair("theUserID", ""));
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
//			request.setEntity(entity);
			
			url += "?theCityCode=" + params[0] + "&theUserID=bb7ccbac2df247da94c48c9f6d87d940";
			HttpGet request = new HttpGet(url);
			
			HttpResponse response = new DefaultHttpClient().execute(request);
//			String res = EntityUtils.toString(response.getEntity());
			
			InputStream is = response.getEntity().getContent();
			byte[] buf = new byte[1024];
			StringBuilder res2 = new StringBuilder();
			int i=-1;
			while((i=is.read(buf))!=-1) {
				res2.append(new String(buf, 0, i, "utf-8"));
			}
			// 获取天气失败 必须传入 theUserID
			Log.i(MainActivity.TAG, res2.toString());
			return "天气信息：" + res2.toString();
//			Log.i(MainActivity.TAG, res);
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "获取天气异常了", e);
		}
		return "获取天气信息失败了";
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
