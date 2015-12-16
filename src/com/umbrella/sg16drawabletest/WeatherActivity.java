package com.umbrella.sg16drawabletest;

import java.util.List;

import com.umbrella.sg16drawabletest.asynctasks.CitiesHttpAsyncTask;
import com.umbrella.sg16drawabletest.asynctasks.ProvincesSOAPAsyncTask;
import com.umbrella.sg16drawabletest.asynctasks.ProvincesHttpAsyncTask;
import com.umbrella.sg16drawabletest.asynctasks.WeatcherHttpAsyncTask;
import com.umbrella.sg16drawabletest.asynctasks.WeatcherSOAPAsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Android WebService KSOAP
 * @author ����
 *
 */
public class WeatherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_weather);
		
		Spinner provinceSpinner = (Spinner)this.findViewById(R.id.province);
		final Spinner citiesSpinner = (Spinner)this.findViewById(R.id.cities);
		
		// ��ȡʡ���б�
		AsyncTask<String, Integer, List<String>> provincesTask = new ProvincesSOAPAsyncTask(this, provinceSpinner);
//		AsyncTask<String, Integer, List<String>> provincesTask = new ProvincesHttpAsyncTask(this, provinceSpinner);
		provincesTask.execute("");
		
		// ��ȡ���У���һ�μ���ʡ���б�ʱ���ᴥ�����¼�
		provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CheckedTextView provinceTv = (CheckedTextView)view;
				Log.i(MainActivity.TAG, "position=" + position + ", id=" + id + ", item=" + provinceTv.getText());
				if(!"��ѡ��ʡ��".equals(provinceTv.getText())) {
					AsyncTask<String, Integer, List<String>> citiesTask = new CitiesHttpAsyncTask(WeatherActivity.this, citiesSpinner);
					citiesTask.execute(new String[]{ provinceTv.getText().toString() });
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		// ��ȡ����
		citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CheckedTextView cityTv = (CheckedTextView)view;
				String city = cityTv.getText().toString();
				Log.i(MainActivity.TAG, "city=" + city);
				if(!"��ѡ�����".equals(city)) {
					AsyncTask<String, Integer, String> weatcherTask = new WeatcherHttpAsyncTask();
//					AsyncTask<String, Integer, String> weatcherTask = new WeatcherSOAPAsyncTask();
					try {
						// ���Դ� ���� �� ID
						Toast.makeText(WeatherActivity.this, 
								weatcherTask.execute(new String[]{ city }).get(), 
								Toast.LENGTH_LONG).show();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}

}
