package com.umbrella.sg16drawabletest;

import java.io.File;
import java.io.IOException;

import com.umbrella.sg16drawabletest.drawable.DynamicDrawableActivity;
import com.umbrella.sg16drawabletest.drawable.GeometricActivity;
import com.umbrella.sg16drawabletest.drawable.GraphEffectsActivity;
import com.umbrella.sg16drawabletest.drawable.TextActivity;
import com.umbrella.sg16drawabletest.movepoint.MovingActivity;
import com.umbrella.sg16drawabletest.threads.SocketTask;
import com.umbrella.sg16drawabletest.threads.URLTask;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String TAG = "sg16drawabletest";
	
	private ImageView zmdaoIv;
	private Button scaleBtn, alphaBtn, translateBtn, rotateBtn;
	
	private ImageView framewalkIv;
	private Button walkBtn, haltBtn;
	private AnimationDrawable walkAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("tag", new Throwable().getStackTrace()[0].getMethodName());
		
		/**
		 * 修改壁纸
		 */
//		changeWall();
		
		/**
		 * Tween 动画
		 */
		this.zmdaoIv = (ImageView)this.findViewById(R.id.zmdao);
		this.scaleBtn = (Button)this.findViewById(R.id.scale);
		// 缩放
		this.scaleBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 硬编码实现
//				Animation animation = new ScaleAnimation(0f, 2f, 0f , 2f,
//						Animation.RELATIVE_TO_SELF, 0.5f,
//						Animation.RELATIVE_TO_SELF, 0.5f);
//				animation.setDuration(3000);
				
				// XML 配置文件实现
				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale);
				
				zmdaoIv.startAnimation(animation);
			}
		});
		// 透明渐变
		this.alphaBtn = (Button)this.findViewById(R.id.alpha);
		this.alphaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Animation animation = new AlphaAnimation(0.1f, 1f);
//				animation.setDuration(3000);
				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
				zmdaoIv.startAnimation(animation);
			}
		});
		// 移动
		this.translateBtn = (Button)this.findViewById(R.id.translate);
		this.translateBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Animation animation = new TranslateAnimation(10, 100, 10, 100);
//				animation.setDuration(3000);
				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
				zmdaoIv.startAnimation(animation);
			}
		});
		// 旋转
		this.rotateBtn = (Button)this.findViewById(R.id.rotate);
		this.rotateBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Animation animation = new RotateAnimation(0, 360,
//						Animation.RELATIVE_TO_SELF, 0.5f,
//						Animation.RELATIVE_TO_SELF, 0.5f);
//				animation.setDuration(3000);
				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
				zmdaoIv.startAnimation(animation);
			}
		});
		
		/**
		 * Frame动画
		 */
		this.framewalkIv = (ImageView)this.findViewById(R.id.framewalk);
		this.walkBtn = (Button)this.findViewById(R.id.walk);
		this.haltBtn = (Button)this.findViewById(R.id.halt);
		this.walkAnim = (AnimationDrawable)framewalkIv.getBackground();
		this.walkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				walkAnim.start();
			}
		});
		this.haltBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				walkAnim.stop();
			}
		});
		
		/**
		 * 动态图形绘制
		 */
		Button dynamicdrawableBtn = (Button)this.findViewById(R.id.dynamicdrawable);
		dynamicdrawableBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DynamicDrawableActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// 几何图形
		Button geometricBtn = (Button)this.findViewById(R.id.geometric);
		geometricBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GeometricActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// 图形特效
		Button graphEffectsBtn = (Button)this.findViewById(R.id.grapheffects);
		graphEffectsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GraphEffectsActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// WebService 天气预报 
		Button weatherBtn = (Button)this.findViewById(R.id.weather);
		weatherBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// GPS
		Button gpsBtn = (Button)this.findViewById(R.id.gps);
		gpsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GPSActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// MovingPoint
		Button movingpointBtn = (Button)this.findViewById(R.id.movingpoint);
		movingpointBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MovingActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// TextActivity
		Button textActivityBtn = (Button)this.findViewById(R.id.textactivity);
		textActivityBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TextActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Socke连接
	 * Http连接
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.sockettest:
			/*
			 * Socket连接
			 * 必须用异步线程的方式进行的Socket连接
			 */
			new Thread(new SocketTask()).start();
			break;
		case R.id.urltest:
//			URLHandler handler = new URLHandler();
//			Message msg = handler.obtainMessage();
//			msg.sendToTarget();
			
			new Thread(new URLTask()).start();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 更改桌面壁纸
	 */
	public void changeWall() {
		try {
			// 这个路径获取的是手机自身内存 /storage/emulated/0
			File extSdCard = Environment.getExternalStorageDirectory();
			Log.i(TAG, extSdCard.getAbsolutePath());
			Log.i(TAG, extSdCard.getCanonicalPath());
			String extSdCardPath = "/storage/extSdCard";
			File pic = new File(extSdCardPath, "pics/6191-100.jpg");
			Log.i(TAG, pic.getAbsolutePath());
			Bitmap bitmap = BitmapFactory.decodeFile(pic.getAbsolutePath());
			this.setWallpaper(bitmap);
			Toast.makeText(this, "壁纸被修改了", Toast.LENGTH_SHORT).show();;
		} catch (IOException e) {
			Log.i(TAG, "异常了", e);
		}
	}
	
}
