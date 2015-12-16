package com.umbrella.sg16drawabletest.asynctasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.umbrella.sg16drawabletest.MainActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * 
 * @author xudazhou
 * 使用 Web Service 获取省份列表
 *
 */
public class ProvincesSOAPAsyncTask extends AsyncTask<String, Integer, List<String>> {
	
	private Context context;
	private Spinner spinner;

	public ProvincesSOAPAsyncTask(Context context, Spinner spinner) {
		super();
		this.context = context;
		this.spinner = spinner;
	}


	@Override
	protected List<String> doInBackground(String... params) {
		String endPoint = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
		String namespace = "http://WebXml.com.cn/";
		String method = "getRegionProvince";
		String soapAction = "http://WebXml.com.cn/getRegionProvince";
		
		// 传入参数
		SoapObject outputSoapObject = new SoapObject(namespace, method);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
		envelope.bodyOut = outputSoapObject;
//		envelope.dotNet = true;
//		new MarshalBase64().register(envelope);
		
		HttpTransportSE transport = new HttpTransportSE(endPoint);
//		transport.debug = true;
		String inputRes = null;
		try {
			transport.call(soapAction, envelope);
			SoapObject inputSoapObject = (SoapObject)envelope.bodyIn;
			if(envelope.getResponse()!=null) {
				inputRes = inputSoapObject.toString();
//				Log.i(MainActivity.TAG, inputRes);
//				parse(inputRes);
			} else {
				Log.i(MainActivity.TAG, "get null provinces");
			}
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "获取省份列表异常了", e);
		}
		return parse2(inputRes);
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
