package com.umbrella.sg16drawabletest.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.umbrella.sg16drawabletest.MainActivity;
import com.umbrella.sg16drawabletest.xmlparser.CitiesParser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CitiesHttpAsyncTask extends AsyncTask<String, Integer, List<String>> {
	
	private Context context;
	private Spinner citiesSpinner;

	public CitiesHttpAsyncTask(Context context, Spinner citiesSpinner) {
		super();
		this.context = context;
		this.citiesSpinner = citiesSpinner;
	}

	@Override
	protected List<String> doInBackground(String... params) {
		String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString";
		try {
			HttpPost request = new HttpPost(url);
			
//			HttpParams param = request.getParams();
//			param.setParameter("theRegionCode", params[0]);
//			request.setParams(param);
			
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("theRegionCode", params[0]));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
			request.setEntity(entity);
		
			HttpResponse response = new DefaultHttpClient().execute(request);
			String res = EntityUtils.toString(response.getEntity());
//			Log.i(MainActivity.TAG, res);
			CitiesParser citiesParser = new CitiesParser();
			return citiesParser.getCities(res);
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "获取城市列表异常", e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item,
				result);
		citiesSpinner.setAdapter(adapter);
	}

}
