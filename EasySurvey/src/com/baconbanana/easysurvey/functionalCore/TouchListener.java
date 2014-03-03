package com.baconbanana.easysurvey.functionalCore;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Class that provides implementation of OnTouchListener.
 * 
 * @author Rafael da Silva Costa & Team
 */
public class TouchListener implements OnTouchListener
{
	private GestureDetector gestureDetector;
	
	/**
	 * Builds a new TouchListener object with the specified context and OnGestureListener.
	 * 
	 * @param context Context of the Activity from which the swipe came.
	 * @param swipeListener Instance of a SwipeListener class with defined actions (abstract methods).
	 */
	public TouchListener(Context context, OnGestureListener gestureListener)
	{
		gestureDetector = new GestureDetector(context, gestureListener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) 
	{
		return gestureDetector.onTouchEvent(me);
	}
}
