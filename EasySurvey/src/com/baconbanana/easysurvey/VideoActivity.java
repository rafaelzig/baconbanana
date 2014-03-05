package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		VideoView video = (VideoView) findViewById(R.id.videoView);

        video.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");

       MediaController mediaController = new MediaController(this);
       mediaController.setAnchorView(video);
       video.setMediaController(mediaController);

       video.start();// test checking
}

		
		

   public void openNewActivity (View view) {
	   Intent intent = new Intent(this, SurveyActivity.class);
	   startActivity(intent);
   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
