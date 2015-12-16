package com.umbrella.sg16drawabletest.drawable;

import com.umbrella.sg16drawabletest.MainActivity;
import com.umbrella.sg16drawabletest.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 绘制几何图形
 * @author 大洲
 * 矩形
 * 椭圆
 * 梯形 路径 渲染
 */
public class GeometricActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView myView = new MyView(this);
		this.setContentView(myView);
	}
	
	class MyView extends View {
		
		private Paint paint;

		public MyView(Context context) {
			super(context);
			paint = new Paint();
			this.setFocusable(true);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Log.i(MainActivity.TAG, "I am onDraw");
//			super.onDraw(canvas);
//			canvas.drawColor(Color.WHITE);
			
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE); // 画线
			paint.setStrokeWidth(3);
			
			// 当两个点重叠时，有图上不显示
			canvas.drawRect(50, 50, 50, 50, paint);
			// 矩形
			canvas.drawRect(100, 100, 200, 150, paint);
			
			// 椭圆
			paint.setColor(Color.BLUE);
			paint.setStyle(Paint.Style.FILL);
			RectF r1 = new RectF(100, 200, 200, 250);
			canvas.drawOval(r1, paint);
			
			/*
			 * 梯形（路径）
			 * 渲染
			 */
			Shader shader = new LinearGradient(0, 0, 100, 100,
					Color.GREEN, Color.YELLOW,
					Shader.TileMode.REPEAT);
			paint.setShader(shader);
			Path p1 = new Path();
			p1.moveTo(125, 300);
			p1.lineTo(100, 400);
			p1.lineTo(200, 400);
			p1.lineTo(175, 300);
			p1.close();
			canvas.drawPath(p1, paint);
			
			paint.setTextSize(24);
			canvas.drawText(this.getResources().getString(R.string.title_activity_geometric), 100, 450, paint);
		}
		
	}

}
