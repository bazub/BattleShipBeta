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

public class ShipPlaceEnd 
extends Activity 
implements View.OnClickListener {
	public int[][] P1= new int[10][10];
    public int[][] P2= new int[10][10];
	public String fname = "battleship"; 
	
	public void writeToFile(){
		try{
			DataOutputStream out = new DataOutputStream(openFileOutput(fname, Context.MODE_PRIVATE));
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P1[i][j]));
					//Log.i("Data Input Sample","m1");
				}
			}
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P2[i][j]));
					//Log.i("Data Input Sample","m2");
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
				if(P1[i][j]==0){
					b.setImageResource(R.drawable.water);
				}
				else if(P1[i][j]==1){
					b.setImageResource(R.drawable.water2);
				}
				else if(P1[i][j]==2){
					b.setImageResource(R.drawable.watered);
				}
				else if(P1[i][j]==11){
					b.setImageResource(R.drawable.e1);
				}
				else if(P1[i][j]==12){
					b.setImageResource(R.drawable.e2);
				}
				else if(P1[i][j]==13){
					b.setImageResource(R.drawable.e3);
				}
				else if(P1[i][j]==14){
					b.setImageResource(R.drawable.e4);
				}
				else if(P1[i][j]==21){
					b.setImageResource(R.drawable.n1);
				}
				else if(P1[i][j]==22){
					b.setImageResource(R.drawable.n2);
				}
				else if(P1[i][j]==23){
					b.setImageResource(R.drawable.n3);
				}
				else if(P1[i][j]==24){
					b.setImageResource(R.drawable.n4);
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
		int x=0,y=0,k,l;
		if (P1[i][j]==2){
			for(k=0;k<=9;k++)
				for(l=0;l<=9;l++)
					if(P1[k][l]==1){
						x=k;
						y=l;
						k=l=10;
					}
			if(y==j){ //ship head=N
				if(x<i){
					P1[x][j]=11;
					P1[x+1][j]=12;
					P1[x+2][j]=13;
					P1[x+3][j]=14;
				}
				else{
					P1[i][j]=11;
					P1[i+1][j]=12;
					P1[i+2][j]=13;
					P1[i+3][j]=14;
				}
			}
			else{ //ship head=E
				if(y<j){
					P1[i][y]=21;
					P1[i][y+1]=22;
					P1[i][y+2]=23;
					P1[i][y+3]=24;
				}
				else{
					P1[i][j]=21;
					P1[i][j+1]=22;
					P1[i][j+2]=23;
					P1[i][j+3]=24;
				}
			}
			for(k=0;k<=9;k++)
				for(l=0;l<=9;l++)
					if(P1[k][l]==2){
						P1[k][l]=0;
					}
			writeToFile();
			int counter=0;
			for(k=0;k<=9;k++)
				for(l=0;l<=9;l++)
					if(P1[l][k]==0)
						counter++;
			if(counter<=100-12)
				finish(); //need to change to new activity
			else{
				Intent intent=new Intent(this,ShipPlace.class);
				startActivity(intent);
				finish();
			}
		}
//		Intent intent = new Intent(this, Player2.class);
		
//		startActivity(intent);
		//finish();
		
		;
	
	}
}
