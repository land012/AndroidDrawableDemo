package com.umbrella.sg16drawabletest.movepoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
/**
 * 
 * @author xudazhou
 * �������Ļĳ��λ��ʱ�����ƶ�����Ӧλ��
 * Handler �����߳�
 *
 */
public class GameView extends View implements OnTouchListener {
	
	// ���������
	private int positionX;
	private int positionY;
	// Ŀ������
	private int targetX;
	private int targetY;
	
	private boolean moving;
	private Handler handler;
	
	private Runnable moveThread = new Runnable() {
		@Override
		public void run() {
			invalidate();
			handler.postDelayed(moveThread, 10); // ÿ 10ms ִ��һ���߳�
		}
		
	};

	public GameView(Context context) {
		super(context);
		this.positionX = 50;
		this.positionY = 50;
		this.moving = false;
		this.handler = new Handler();
		this.setOnTouchListener(this);
	}

	/**
	 * ����View
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		move();
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		Rect sq = new Rect(positionX, positionY, positionX+50, positionY+50);
		canvas.drawRect(sq, p);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(!moving) {
			targetX = (int)event.getX();
			targetY = (int)event.getY();
			moving = true;
			handler.postAtTime(moveThread, 1); // boot 1ms �������߳�
		}
		return true;
	}
	
	private void move() {
		if(positionX > targetX) positionX--;
		if(positionX < targetX) positionX++;
		if(positionY > targetY) positionY--;
		if(positionY < targetY) positionY++;
		if(positionX == targetX && positionY == targetY && moving) {
			handler.removeCallbacks(moveThread); // ִֹͣ���߳� moveThread
			moving = false;
			Intent intent = new Intent();
			intent.setClass(getContext(), ResultActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("msg", "I am arrived at x=" + targetX + ", y=" + targetY);
			intent.putExtras(bundle);
			this.getContext().startActivity(intent);
		}
	}

}
