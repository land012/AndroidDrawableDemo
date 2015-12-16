package com.umbrella.sg16drawabletest.demo.plusgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View {

	private Random rnd = new Random();
	private int[] numberArr;
	private boolean[] numberArrFlag;

	public GameView(Context context) {
		super(context);
	}
	
	public void init() {
		numberArr = new int[16];
		numberArrFlag = new boolean[16];
	}
	
	public void genNumberArr() {
		for(int i=0; i<this.numberArr.length; i++) {
			numberArr[i] = rnd.nextInt(10);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
