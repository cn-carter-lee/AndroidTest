package com.liwuso.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

	public CustomImageView(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			Drawable drawable = getDrawable();

			if (drawable == null) {
				setMeasuredDimension(0, 0);
			} else {
				int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
				int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
				if (measuredHeight == 0 && measuredWidth == 0) {
					// Height and width set to wrap_content
					setMeasuredDimension(measuredWidth, measuredHeight);
				} else if (measuredHeight == 0) {
					// Height set to wrap_content
					int width = measuredWidth;
					int height = width * drawable.getIntrinsicHeight()
							/ drawable.getIntrinsicWidth();
					setMeasuredDimension(width, height);
				} else if (measuredWidth == 0) {
					// Width set to wrap_content
					int height = measuredHeight;
					int width = height * drawable.getIntrinsicWidth()
							/ drawable.getIntrinsicHeight();
					setMeasuredDimension(width, height);
				} else {
					// Width and height are explicitly set (either to
					// match_parent or to exact value)
					setMeasuredDimension(measuredWidth, measuredHeight);
				}

			}
		} catch (Exception e) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}
}