package com.baconbanana.easysurvey;

import java.util.Random;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Bubble extends ImageView{
	private int xPos;
	private int yPos;
	private Random randy;
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
		randy = new Random();
		return randy.nextInt(100) + 50;
	}
	public void doAnimation(){
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 1000f);
			anim.setDuration(1000);
			anim.start();
		
	}
}
