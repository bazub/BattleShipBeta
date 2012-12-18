package com.example.loadingscreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Button;

 
public class MainActivity extends Activity {
	
	Button btnStartProgress;
	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
 
	private long counter = 0;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
 
		loading();
 
	}
 
	public void loading() {
		progressBar = new ProgressDialog(this);
		progressBar.setCancelable(false);
		progressBar.setMessage("              The game is loading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setMax(100);
		progressBar.show();
		progressBar.setProgressPercentFormat(null);
		new Thread(new Runnable() {
			   public void run() {
				   while (progressBarStatus < 100) {
					   progressBarStatus = increaseCounter();

					   progressBarHandler.post(new Runnable() {
						   public void run() {
							   progressBar.setProgress(progressBarStatus);
							   if(progressBarStatus<33){
								   progressBar.setProgressNumberFormat("Creating game map                             ");
							   }
							   else if(progressBarStatus<67){
								   progressBar.setProgressNumberFormat("Destroying pirate ships                       ");
							   }
							   else{
								   progressBar.setProgressNumberFormat("Placing enemy's battleships                    ");
							   }
						   }
					   });
				   }
				   if (progressBarStatus >= 100) {
					   // 	sleep 2 seconds, so that you can see the 100%
					   try {
						   Thread.sleep(2000);
					   } catch (InterruptedException e) {
						   e.printStackTrace();
					   }
					   // close the progress bar dialog
					   progressBar.dismiss();
				   	}
			   	}
		}).start();

	}
	public int increaseCounter() {
		while (counter <= 20000000) {
			counter++;
			if(counter%200000==0)
				return (int)counter/200000;
		}
		return 0;
	}
 
}