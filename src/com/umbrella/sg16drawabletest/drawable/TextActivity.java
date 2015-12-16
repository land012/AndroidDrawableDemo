package com.umbrella.sg16drawabletest.drawable;

import com.umbrella.sg16drawabletest.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * 写文字
 * @author 大洲
 * 画文字时，文字的左下角是起始坐标
 */
public class TextActivity extends Activity {
	
	private static final String TAG = "TextActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MyView myView = new MyView(this);
		this.setContentView(myView);
	}
	
	/**
	 * View
	 * @author 大洲
	 *
	 */
	class MyView extends View {
		
		private Handler handler = new Handler();
		private int treeDepth = 1;

		public MyView(Context context) {
			super(context);
			this.setBackgroundResource(R.drawable.bg001);
			this.handler.post(new RefreshThread(this));
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Rect bounds = new Rect();
			Paint paint = new Paint();
			
			// 透明矩形
			Rect r1 = new Rect(0, 100, 1080, 200);
			paint.setARGB(100, 00, 80, 00);
			canvas.drawRect(r1, paint);
			
			// 文字
			paint.setTextSize(100);
			paint.setColor(Color.BLUE);
			paint.setAlpha(10); // 透明度，数越小越透明
			String text = "我";
			paint.getTextBounds(text, 0, 1, bounds);
			Log.i(TAG, bounds.left + "," + bounds.top + "," + bounds.right + "," + bounds.bottom); // 0,-10,12,1
			canvas.drawText(text, 300, 300 - bounds.top, paint);
			
			paint.setColor(Color.GREEN);
			canvas.drawLine(0, 300, 1080, 300, paint); // 300
			canvas.drawLine(0, 300 - bounds.top, 1080, 300 - bounds.top, paint); // 378
			canvas.drawLine(0, 400, 1080, 400, paint); // 400
			
			// 二次曲线
			paint.setStyle(Paint.Style.STROKE);
			paint.setAlpha(255);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(5);
			Path quad1 = new Path();
			quad1.setLastPoint(100, 600); // 起始点
			quad1.quadTo(450, 400, 800, 600); // 最高点 和 终点
			canvas.drawPath(quad1, paint);
			
			quad1.reset();
			quad1.setLastPoint(100, 650);
			quad1.quadTo(300, 800, 100, 950);
			canvas.drawPath(quad1, paint);
			
			Bitmap bitmap1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			bitmap1.eraseColor(Color.rgb(0, 128, 0));
			for(int i=0; i<50; i++) {
				bitmap1.setPixel(50+i, 50, Color.WHITE);
				bitmap1.setPixel(50, 50+i, Color.WHITE);
			}
			Canvas canvas1 = new Canvas(bitmap1);
			canvas1.drawLine(20, 20, 50, 50, paint);
			canvas1.save(Canvas.ALL_SAVE_FLAG);
			canvas.drawBitmap(bitmap1, 100, 1000, paint);
			
			Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.belle03);
			Matrix matrix2 = new Matrix();
			matrix2.preTranslate(400, 1000); // 指定坐标
			matrix2.preScale(1, 1); // 缩放
			canvas.drawBitmap(bitmap2, matrix2, null);
			
//			drawFX(canvas, paint, 5, new PointF(540, 1900), 64, 0.75, -Math.PI/2);
			drawFX(canvas, paint, 1, new PointF(540, 1900), 64, 0.75, -Math.PI/2);
		}
		
		/**
		 * 绘制分形效果
		 * @param canvas
		 * @param paint
		 * @param depth
		 * @param point
		 * @param length
		 * @param lengthFactor
		 * @param theta
		 */
		private void drawFX(Canvas canvas, 
				Paint paint, 
				int depth, 
				PointF point, 
				double length, 
				double lengthFactor, 
				double theta) {
			if(depth%2==0) {
				paint.setColor(Color.GREEN);
			} else{
				paint.setColor(Color.RED);
			}
			float x1 = (float)(point.x + length * Math.cos(theta));
			float y1 = (float)(point.y + length * Math.sin(theta));
			canvas.drawLine(point.x, point.y, x1, y1, paint);
			if(depth<this.treeDepth) {
				drawFX(canvas, paint, depth+1, new PointF(x1, y1), length*lengthFactor, lengthFactor, theta + Math.PI/5);
				drawFX(canvas, paint, depth+1, new PointF(x1, y1), length*lengthFactor, lengthFactor, theta - Math.PI/5);
			} else {
				return;
			}
		}
		
		class RefreshThread implements Runnable {
			
			private MyView myView;

			public RefreshThread(MyView myView) {
				super();
				this.myView = myView;
			}

			@Override
			public void run() {
				myView.treeDepth++;
				if(myView.treeDepth>5) myView.treeDepth=1;
				myView.invalidate();
				handler.postDelayed(this, 1000);
			}
			
		}
		
	}

}
