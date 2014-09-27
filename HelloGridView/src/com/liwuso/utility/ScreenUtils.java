package com.liwuso.utility;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtils {

	public static float getWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = activity.getResources().getDisplayMetrics().density;
		float dpHeight = outMetrics.heightPixels / density;
		float dpWidth = outMetrics.widthPixels / density;

		return dpHeight;
	}

	public static float getPixelWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		return outMetrics.widthPixels;
	}

}
