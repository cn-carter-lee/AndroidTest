package com.liwuso.app;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.liwuso.app.ui.Main;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class AppStart extends Activity {

	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		// Analytics service
		MobclickAgent.setDebugMode(true);
		MobclickAgent.updateOnlineConfig(this);
	    // Push service		
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		this.initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initView() {
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
		aa.setDuration(3000);
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