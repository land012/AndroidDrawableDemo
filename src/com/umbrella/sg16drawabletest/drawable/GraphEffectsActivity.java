package com.umbrella.sg16drawabletest.drawable;

import com.umbrella.sg16drawabletest.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * 图形特效
 * @author 大洲
 * 点击选项菜单按钮，渲染不同的图形效果
 * 使用 Matrix 变换图片
 */
public class GraphEffectsActivity extends Activity {
	
	private static final String TAG = "GraphEffectsActivity";
	
	ShaderView shaderView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyView myView = new MyView(this);
		this.setContentView(myView);
		
		myView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyView view = (MyView)v;
				view.rotate();
			}
		});
		
		// 渲染
		shaderView = new ShaderView(this);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.graph_effects, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, item.getItemId() + "," + item.getTitle());
		this.shaderView.changeShader(item.getItemId());
		/*
		 * 调用 setContentView时，会执行 View 的 OnDraw()方法
		 */
		this.setContentView(shaderView);
		return true;
	}


	/**
	 * 使用 Matrix 旋转图片
	 * @author 大洲
	 *
	 */
	class MyView extends View {
		
		private Paint paint;
		
		private Matrix matrix;
		private Bitmap bm;
		private Bitmap bm2;
		private int w;
		private int h;
		private float angle=0.0f; // 旋转的角度

		public MyView(Context context) {
			super(context);
			paint = new Paint();
			matrix = new Matrix();
			bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.shibata);
			bm2 = Bitmap.createBitmap(bm);
			w = bm.getWidth();
			h = bm.getHeight();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(bm2, 400, 400, null);
			
			// 边框
			paint.setColor(Color.GREEN);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(399, 399, 401+w, 401+h, paint);
		}
		
		/**
		 * 旋转
		 */
		public void rotate() {
			angle += 30;
			matrix.reset();
			matrix.setRotate(angle);
			bm2 = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
			this.postInvalidate();
		}
		
	}
	
	/**
	 * 渲染图形
	 * @author 大洲
	 *
	 */
	class ShaderView extends View {
		
		Bitmap bm;
		Paint paint;
		int[] colors;
		Shader bitmapShader;
		Shader linearGradient; // 线性渲染
		Shader radialGradient; // 光束渲染
		Shader sweepGradient; // 梯度渲染
		Shader composeShader; // 混合渲染

		public ShaderView(Context context) {
			super(context);
			paint = new Paint();
			colors = new int[]{ Color.RED, Color.GREEN, Color.BLUE };
			
			bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.shibata);
			bitmapShader = new BitmapShader(bm, TileMode.REPEAT, TileMode.MIRROR);
			linearGradient = new LinearGradient(0, 0, 100, 100, colors, null, TileMode.REPEAT);
			Log.i(TAG, "width1=" + this.getWidth());
			
			sweepGradient = new SweepGradient(200, 200, colors, null);
			
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Log.i(TAG, "width2=" + this.getWidth());
			canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
		}

		public void changeShader(int id) {
			switch(id) {
			case R.id.graph_effects_om_bitmapshader:
				paint.setShader(bitmapShader);
				break;
			case R.id.graph_effects_om_lineargradient:
				paint.setShader(linearGradient);
				break;
			case R.id.graph_effects_om_radialgradient:
				radialGradient = new RadialGradient(this.getWidth()/2, this.getHeight()/2, 50, colors, null, TileMode.REPEAT);
				paint.setShader(radialGradient);
				break;
			case R.id.graph_effects_om_sweepgradient:
				paint.setShader(sweepGradient);
				break;
			case R.id.graph_effects_om_composeshader:
				radialGradient = new RadialGradient(100, 100, 50, colors, null, TileMode.REPEAT);
				composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.SRC);
				paint.setShader(composeShader);
				break;
			default:
				break;
			}
		}
		
	}

}
