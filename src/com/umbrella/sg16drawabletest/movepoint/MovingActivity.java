package com.umbrella.sg16drawabletest.movepoint;

import com.umbrella.sg16drawabletest.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
/**
 * Moving Point
 * @author 大洲
 * 在屏幕上点击，蓝色小方块会移动到点击的位置，之后弹出对话框
 */
public class MovingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameView gameView = new GameView(this);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(gameView);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(MainActivity.TAG, "keyCode=" + keyCode + "," + event.getKeyCode());
		// 返回键 返回到 MainActivity
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			/**
			 * 这样相当于从 MovigActivity 跳至 MainActivity，
			 * 这样的话，当在 MainActivity 按下返回时，会跳至 MovingActivity，陷入循环
			 */
//			Intent intent = new Intent();
//			intent.setClass(this, MainActivity.class);
//			this.startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

}
