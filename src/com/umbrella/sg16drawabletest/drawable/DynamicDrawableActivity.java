package com.umbrella.sg16drawabletest.drawable;

import com.umbrella.sg16drawabletest.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * ��̬ͼ�λ���
 * @author ����
 * һ���ƶ���Բ�㣬�����Ļʱ��ֹͣ�ƶ�
 * �Զ��� Handler ���� Message
 */
public class DynamicDrawableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Paint paint = new Paint();
		MyView v = new MyView(this, paint);
		setContentView(v);
		
		/**
		 *  handlerʵ��Ҫ�����߳�������������
		 *  E/AndroidRuntime(4457): android.view.ViewRootImpl$CalledFromWrongThreadException: 
		 *  Only the original thread that created a view hierarchy can touch its views.
		 */
		RefreshHandler handler = new RefreshHandler(v);
		
		final Thread refreshThread = new Thread(new RefreshTask(handler));
		refreshThread.start();
		
		// �����Ļʱ��Բ��ֹͣ�ƶ�
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(DynamicDrawableActivity.this, "Click me", Toast.LENGTH_LONG).show();
				refreshThread.interrupt();
			}
		});
	}
	
	/**
	 * 
	 * @author ����
	 *
	 */
	class MyView extends View {
		
		private static final int DEFAULT_X = 100;
		private static final int DEFAULT_Y = 50;
		private static final int STEP = 5;
		
		private int x = DEFAULT_X;
		private int y = DEFAULT_Y;
		
		private Paint paint;

		public MyView(Context context, Paint paint) {
			super(context);
			this.setFocusable(true);
			this.paint = paint;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			paint.setColor(Color.GREEN);
			canvas.drawCircle(x, y, 10, paint);
		}
		
		public void move() {
			int h = this.getHeight();
			y += STEP;
			if(y>=h) y=DEFAULT_Y;
			Log.i(MainActivity.TAG, "I like move y=" + y);
		}
		
	}
	
	/**
	 * handler
	 * ����������ˢ�²���
	 * @author ����
	 *
	 */
	static class RefreshHandler extends Handler {
		
		private MyView myView;

		public RefreshHandler(MyView myView) {
			super();
			this.myView = myView;
		}

		@Override
		public void handleMessage(Message msg) {
			Log.i(MainActivity.TAG, "msg.what=" + msg.what);
			
			myView.move();
			myView.invalidate();
			super.handleMessage(msg);
		}
		
	}
	
	/**
	 * ���߳�
	 * �첽����ˢ����Ϣ
	 * @author ����
	 *
	 */
	class RefreshTask implements Runnable {
		
		private RefreshHandler handler;
		private double d = 0.0;
		
		public RefreshTask(RefreshHandler handler) {
			super();
			this.handler = handler;
		}
		
		@Override
		public void run() {
			Log.i(MainActivity.TAG, "I am RefreshTask run");
			
			while(!Thread.currentThread().isInterrupted()) {
//					Log.i(MainActivity.TAG, "running");
				
				Message m = handler.obtainMessage();
				m.sendToTarget();
				// ��ʱ����
				for(int i=0; i<10000000; i++) {
					d = d + (Math.PI + Math.E)/d;
				}
			}
		}
	}

}
