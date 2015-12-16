package com.umbrella.sg16drawabletest.demo.plusgame;

import com.umbrella.sg16drawabletest.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * º”∑®”Œœ∑
 * @author xudazhou
 *
 */
public class PlusGameActivity extends Activity {
	GameView gameView;
	Button reset, complete;
	TextView score, goal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_plusgame);
	}

}
