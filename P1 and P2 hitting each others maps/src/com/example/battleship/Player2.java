package com.example.battleship;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Player2 
extends Activity 
implements View.OnClickListener{
	//public final static int[][] M1=new int[10][10];
	//public final static int[][] M2=new int[10][10];
	public int[][] P1= new int[10][10];
    public int[][] P2= new int[10][10];
	public String fname = "battleship"; 
	
    public void writeToFile(){
		try{
			DataOutputStream out = new DataOutputStream(openFileOutput(fname, Context.MODE_PRIVATE));
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P1[i][j]));
				}
			}
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P2[i][j]));
				}
			}
			out.close();
		}
		catch (IOException e){
			Log.i("Data Input Sample","I/O Error");
		}
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
				
		try{ //try to get matrix config
			String fname="battleship";
			File file = getBaseContext().getFileStreamPath(fname);
		       if(file.exists()){ //read from file
		    	   DataInputStream in = new DataInputStream(openFileInput(fname));
		           for(int i=0;i<10;i++){
		        	   for(int j=0;j<10;j++){
		        		   P1[i][j]=Integer.parseInt(in.readUTF());
		        	   }
		           }
		           for(int i=0;i<10;i++){
		        	   for(int j=0;j<10;j++){
		        		   P2[i][j]=Integer.parseInt(in.readUTF());
		        		   Log.i(fname, Integer.toString(P2[i][j]));
		        	   }
		           }
		           in.close();
		       }
		       else{ //file doesn't exist so we create it
		    	   DataOutputStream out = new DataOutputStream(openFileOutput(fname, Context.MODE_PRIVATE));
				   for (int i=0; i<10; i++) {
					   for (int j=0; j<10;j++){
						   P1[i][j]=0;
						   out.writeUTF(Integer.toString(0));
					   }
				   }
				   for (int i=0; i<10; i++) {
					   for (int j=0; j<10;j++){
						   P2[i][j]=1;
						   out.writeUTF(Integer.toString(1));
					   }
				   }
				   out.close();
		       }   
		
		}
		catch (IOException e){
			Log.i("Data Input Sample","I/O Error");
		}
		
		super.onCreate(savedInstanceState);
		
		ScrollView largelay = new ScrollView (this);
		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(300,300) );
		layout.setPadding(1,1,1,1);

		for (int i=0; i<=9; i++) {
			TableRow tr = new TableRow(this);
			for (int j=0; j<=9; j++) {
				ImageButton b = new ImageButton (this);
				if(P2[i][j]==0){
					b.setImageResource(R.drawable.water2);
				}
				else {
					b.setImageResource(R.drawable.water);
				}
				b.setId(i*10+j);
				b.setOnClickListener(this);
				tr.addView(b, 72,72);
			} // for
			layout.addView(tr);
		} // for
		largelay.addView(layout);
		super.setContentView(largelay);
	}	 // ()
    public void onClick(View view) {
		int id=view.getId();
		int i=id/10;
		int j=id%10;
		P2[i][j]=1;
		((ImageButton) view).setImageResource(R.drawable.water);
		Intent intent = new Intent(this, MainActivity.class);
		writeToFile();
		startActivity(intent);
		finish();
		
		;
	
	}
}