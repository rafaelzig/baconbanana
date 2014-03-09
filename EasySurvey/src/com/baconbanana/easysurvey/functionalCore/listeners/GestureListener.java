/**
 * 
 */
package com.baconbanana.easysurvey.functionalCore.listeners;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * Abstract class that provides implementations of left to right and right to
 * left swipe gestures
 * 
 * @author Rafael da Silva Costa & Team
 */
public abstract class GestureListener extends SimpleOnGestureListener
{
	/**
	 * Amount of pixels the swipe can be off at max.
	 */
	private final int MAX_OFF_PATH = 200;

	/**
	 * Minimum distance of the swipe.
	 */
	private final int MIN_DISTANCE = 120;

	/**
	 * Maximum velocity of the swipe.
	 */
	private final int MAX_VELOCITY = 200;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{

		// If swipe is not too far from Y axis
		if (Math.abs(e1.getY() - e2.getY()) <= MAX_OFF_PATH)
		{
			//if the swipe is long and fast enough
			if(e1.getX() - e2.getX() > MIN_DISTANCE && Math.abs(velocityX) > MAX_VELOCITY)
				return onRightToLeftSwipe();
			
			//if the swipe is long and fast enough
		    else if (e2.getX() - e1.getX() > MIN_DISTANCE && Math.abs(velocityX) > MAX_VELOCITY) 
				return onLeftToRightSwipe(); 	
		}
		//if the swipe is not too far from X axis
		else if (Math.abs(e1.getX() - e2.getX()) <= MAX_OFF_PATH)
		{
			//if the swipe is long and fast enough
			if (e1.getY() - e2.getY() > MIN_DISTANCE && Math.abs(velocityY) > MAX_VELOCITY)
				return onBottomToTopSwipe();
			
			//if the swipe is long and fast enough
		    else if (e2.getY() - e1.getY() > MIN_DISTANCE && Math.abs(velocityY) > MAX_VELOCITY)
				return onTopToBottomSwipe();
		}

		return false;
	}

	/**
	 * Method triggered when a swipe from left to right of the screen is
	 * identified.
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onLeftToRightSwipe();

	/**
	 * Method triggered when a swipe from right to left of the screen is
	 * identified.
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onRightToLeftSwipe();

	/**
	 * Method triggered when a swipe from bottom to top of the screen is
	 * identified.
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onBottomToTopSwipe();

	/**
	 * Method triggered when a swipe from top to bottom of the screen is
	 * identified.
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onTopToBottomSwipe();

	@Override
	public boolean onDown(MotionEvent e)
	{
		return true;
	}
}