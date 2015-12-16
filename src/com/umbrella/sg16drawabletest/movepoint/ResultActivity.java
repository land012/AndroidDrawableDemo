package com.umbrella.sg16drawabletest.movepoint;

import com.umbrella.sg16drawabletest.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Caused by: java.lang.NullPointerException
//		Log.i(MainActivity.TAG, "I am ResultActivity, msg=" + savedInstanceState.getString("msg"));
		final String msg = this.getIntent().getExtras().getString("msg");
		Log.i(MainActivity.TAG, "I am ResultActivity, msg=" + msg);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("I am Result")
			.setMessage(msg)
			.setPositiveButton("Back", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					Intent intent = new Intent();
//					intent.setClass(ResultActivity.this, MovingActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("name", "Susanoo");
//					intent.putExtras(bundle);
//					startActivity(intent);
					ResultActivity.this.finish();
				}
			});
		builder.create().show();
	}

}
