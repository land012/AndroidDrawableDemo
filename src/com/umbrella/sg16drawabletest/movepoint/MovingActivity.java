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
 * @author ����
 * ����Ļ�ϵ������ɫС������ƶ��������λ�ã�֮�󵯳��Ի���
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
		// ���ؼ� ���ص� MainActivity
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			/**
			 * �����൱�ڴ� MovigActivity ���� MainActivity��
			 * �����Ļ������� MainActivity ���·���ʱ�������� MovingActivity������ѭ��
			 */
//			Intent intent = new Intent();
//			intent.setClass(this, MainActivity.class);
//			this.startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

}
