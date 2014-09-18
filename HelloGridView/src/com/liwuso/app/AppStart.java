package com.liwuso.app;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.liwuso.app.ui.Main;

public class AppStart extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(this, R.layout.start, null);
		RelativeLayout relativeLayout = (RelativeLayout) view
				.findViewById(R.id.starter_layout);
		Random rand = new Random();
		if (rand.nextInt() % 2 == 0)
			relativeLayout.setBackgroundResource(R.drawable.welcome_1);
		else
			relativeLayout.setBackgroundResource(R.drawable.welcome_2);
		setContentView(view);

		AlphaAnimation aa = new AlphaAnimation(1.0f, 1.0f);
		aa.setDuration(4000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}

	private void redirectTo() {
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
	}
}