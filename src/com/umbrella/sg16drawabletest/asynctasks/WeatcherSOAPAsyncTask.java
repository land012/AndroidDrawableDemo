package com.umbrella.sg16drawabletest.asynctasks;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.umbrella.sg16drawabletest.MainActivity;

import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author xudazhou
 * 
 *
 */
public class WeatcherSOAPAsyncTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.i(MainActivity.TAG, "getWeather, theCityCode=" + params[0]);
			
			String endPoint = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
			String namespace = "http://WebXml.com.cn/";
			String method = "getWeather";
			String soapAction = "http://WebXml.com.cn/getWeather";
			
			SoapObject output = new SoapObject(namespace, method);
			output.addProperty("theCityCode", params[0]);
			output.addProperty("theUserID", "bb7ccbac2df247da94c48c9f6d87d940");
//			output.addAttribute("theCityCode", params[0]);
//			output.addAttribute("theUserID", "");
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			envelope.bodyOut = output;
			
			HttpTransportSE transport = new HttpTransportSE(endPoint);
			transport.call(soapAction, envelope);
			
//			SoapObject input = (SoapObject)envelope.bodyIn;
			
			// Code: soap:Receiver, Reason: 服务器无法处理请求。 ---
			Log.i(MainActivity.TAG, envelope.bodyIn.toString());
		} catch (Exception e) {
			Log.i(MainActivity.TAG, "获取天气异常了", e);
		}
		return null;
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
