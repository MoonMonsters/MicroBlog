package com.newer.newerblogging.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;

/**
 * 实现跑马灯效果
 */
public class MarqueeText extends TextView {
	public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MarqueeText(Context context) {
		super(context);
	}
	
	@Override
	@ViewDebug.ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
}