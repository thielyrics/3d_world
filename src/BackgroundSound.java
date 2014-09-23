import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.io.*;


public class BackgroundSound {
	  	AudioFormat audioFormat;
	  	AudioInputStream audioInputStream;
	  	SourceDataLine sourceDataLine;
	    boolean stopPlayback = false;
	    Play_Audio p ;
	    
	     public BackgroundSound(){
	    	 p = new Play_Audio();
	     }

	     public void play(File soundFile)
	     {
	          try{
	               audioInputStream = AudioSystem.getAudioInputStream(soundFile); 
	               audioFormat = audioInputStream.getFormat(); 
	               
	               DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat); 
	          
	               sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo); 
	               stopPlayback   = false; 
	               p.start(); 
	               
	          }
	          catch (Exception error)
	          {
	               JOptionPane.showMessageDialog(null, "The audio file cannot be opened!", 
	                    "Error!", JOptionPane.INFORMATION_MESSAGE);
	          }
	     }
	     
	     private class Play_Audio extends Thread{
	          byte buffer[] = new byte[100000]; 
	          public void run(){
	               try{
	                     sourceDataLine.open(audioFormat); 
	                     sourceDataLine.start();
	     
	                     int continue_play;
	                     while((continue_play = audioInputStream.read(buffer,0,buffer.length)) != -1 && stopPlayback == false){
	                     
	                         if(continue_play > 0){
	                              sourceDataLine.write(buffer, 0, continue_play);
	                         }
	                     }
	                     sourceDataLine.drain(); 
	                     sourceDataLine.close(); 
	               }
	               catch(Exception e){
	            	 //System.out.println("Should be playing audio, but my computer is dumb");
	            	 //System.out.println("cannot open the song");
	            	 ////////////////fix this///////////////////////
	               }
	          }
	     }
}
