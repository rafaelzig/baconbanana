/**
 * 
 */
package com.baconbanana.easysurvey.functionalCore;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

/**
 * Abstract class that provides implementations of left to right and right to left swipe gestures
 * 
 * @author Rafael da Silva Costa & Team
 */
public abstract class GestureListener extends SimpleOnGestureListener
{
	private final int SWIPE_MAX_OFF_PATH = 200; // # of Pixels the swipe can be off at max
	private final int SWIPE_MIN_DISTANCE = 120; // Minimum distance of the swipe
	private final int SWIPE_MAX_VELOCITY = 200; // Maximum velocity of the swipe
	
	@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
		if (Math.abs(e1.getY() - e2.getY()) <= SWIPE_MAX_OFF_PATH) // If the swipe is not too far from Y axis
		{
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MAX_VELOCITY) // If the swipe is long and fast enough
				return onRightToLeftSwipe();
		    else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MAX_VELOCITY)  // If the swipe is long and fast enough
				return onLeftToRightSwipe(); 	
		}
        return false;
    }
	
	@Override
    public boolean onDown(MotionEvent e) 
	{
        return true;
	}
        
	/**
	 * Method triggered when a swipe from left to right of the screen is identified.
	 * The swipe must meet basic parameters which are always adjusted to screen rotation. 
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onLeftToRightSwipe();
	
	/**
	 * Method triggered when a swipe from right to left of the screen is identified.
	 * The swipe must meet basic parameters which are always adjusted to screen rotation. 
	 * 
	 * @return TRUE if action happened, FALSE if not.
	 */
	public abstract boolean onRightToLeftSwipe();
}