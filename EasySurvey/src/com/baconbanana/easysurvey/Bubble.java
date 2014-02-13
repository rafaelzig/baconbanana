package com.baconbanana.easysurvey;

import java.util.Random;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Bubble extends ImageView{
	private int xPos;
	private int yPos;
	private Random randy = new Random();
	private RelativeLayout.LayoutParams params;
	public Bubble(Context context){
		super(context);
		init();
	}
	public float getXPos(){
		return xPos;
	}
	public void setXPos(int x){
		xPos = x;
	}
	public float getYPos(){
		return yPos;
	}
	public void setYPos(int y){
		yPos = y;
	}
	private void init(){
		setWillNotDraw(false);
		this.setImageResource(R.drawable.bubblecope);
	}
	public void setPosition(int[] pos){
		int size = calSize();
		params = new RelativeLayout.LayoutParams(size, size);
		params.leftMargin = xPos = pos[0];
		params.topMargin = yPos = pos[1];
		this.setLayoutParams(params);
		System.out.println(this.getWidth());
		this.invalidate();
	}

	private int calSize(){
		return randy.nextInt(100) + 50;
	}
	public void doAnimation(){
			ObjectAnimator anima = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f);
			anima.setDuration(5);
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, -(yPos) - 300);
			ObjectAnimator animx = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, randy.nextInt(100) + 50);
			animx.setRepeatCount(5);
			animx.setRepeatMode(Animation.REVERSE);
			ObjectAnimator animr = ObjectAnimator.ofFloat(this, "rotate", 360);
			anima.setDuration(randy.nextInt(100));
			animx.setRepeatCount(5);
			animx.setRepeatMode(Animation.RESTART);
			AnimatorSet animSet = new AnimatorSet();
			animSet.play(animr).with(anim);
			animSet.play(anima).with(anim);
			animSet.playTogether(anim, animx);
			animSet.setDuration(randy.nextInt(3000) + 1000);
			animSet.setInterpolator(new DecelerateInterpolator());
			animSet.start();
	}
}
