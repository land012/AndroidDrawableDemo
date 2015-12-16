package com.umbrella.sg16drawabletest.asynctasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.umbrella.sg16drawabletest.MainActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ProvincesHttpAsyncTask extends AsyncTask<String, Integer, List<String>> {
	
	private Context context;
	private Spinner spinner;

	public ProvincesHttpAsyncTask(Context context, Spinner spinner) {
		super();
		this.context = context;
		this.spinner = spinner;
	}


	@Override
	protected List<String> doInBackground(String... params) {
		String res = null;
		try {
			String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getRegionProvince";
			
//			HttpPost request = new HttpPost(url);
			
			HttpGet request = new HttpGet(url);
			
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			
			res = EntityUtils.toString(response.getEntity());
			Log.i(MainActivity.TAG, res);
		} catch(Exception e) {
			Log.i(MainActivity.TAG, "获取省份列表异常了", e);
		}
		
		return parse2(res);
	}


	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(List<String> result) {
		super.onPostExecute(result);
//		Log.i(MainActivity.TAG, result.toString());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,
				android.R.layout.simple_spinner_dropdown_item,
				result);
		spinner.setAdapter(adapter);
	}
	
	private Map<String, String> parse(String param) {
		Map<String, String> res = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("string=([^;]+);");
		Matcher matcher = pattern.matcher(param);
		while(matcher.find()) {
			String s = matcher.group(1);
//			Log.i(MainActivity.TAG, s);
			String[] arr = s.split(",");
			res.put(arr[1], arr[0]);
		}
		return res;
	}
	
	private List<String> parse2(String param) {
		List<String> res = new ArrayList<String>();
		res.add("请选择省份");
		if(param!=null && !"".equals(param)) {
			Pattern pattern = Pattern.compile("string=([^;]+);");
			Matcher matcher = pattern.matcher(param);
			while(matcher.find()) {
				String s = matcher.group(1);
//			Log.i(MainActivity.TAG, s);
				String[] arr = s.split(",");
				res.add(arr[0]);
			}
		}
		return res;
	}
}
