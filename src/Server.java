

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Server {
	ServerSocket MyService;
	Socket clientSocket = null;
	BufferedInputStream input;
	TargetDataLine targetDataLine;

	BufferedOutputStream out;
	ByteArrayOutputStream byteArrayOutputStream;
	AudioFormat audioFormat;	
	
	SourceDataLine sourceDataLine;	  
	byte tempBuffer[] = new byte[10000];
	
	Server(JFrame parent) throws LineUnavailableException{   
    	try {
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo =  new DataLine.Info( SourceDataLine.class,audioFormat);
            sourceDataLine = (SourceDataLine)
    	    AudioSystem.getLine(dataLineInfo);
    	    sourceDataLine.open(audioFormat);
    	    sourceDataLine.start();
			MyService = new ServerSocket(8080);
                        
                        
                        JOptionPane.showMessageDialog(null, "Waiting request from client...");
			clientSocket = MyService.accept();
                        
                        JOptionPane.showMessageDialog(null, "Accepting request from client witch IP = " + clientSocket.getInetAddress().getHostAddress());
                        
                        ((AudioTab)parent).addParticipant(clientSocket.getInetAddress().getHostAddress());
                        
			captureAudio();
			input = new BufferedInputStream(clientSocket.getInputStream());	
			out=new BufferedOutputStream(clientSocket.getOutputStream());
			
                        //this is maybe to give u the output of the voice
			while(input.read(tempBuffer)!=-1){			
				sourceDataLine.write(tempBuffer,0,10000);
                                System.out.println("Suara Masuk");
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
       
	}
	 private AudioFormat getAudioFormat(){
		    float sampleRate = 8000.0F;		  
		    int sampleSizeInBits = 16;		   
		    int channels = 1;		    
		    boolean signed = true;		    
		    boolean bigEndian = false;		 
		    return new AudioFormat(
		                      sampleRate,
		                      sampleSizeInBits,
		                      channels,
		                      signed,
		                      bigEndian);
		  }
	
	private void captureAudio() {
		try {
			
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
			}
			audioFormat = getAudioFormat();

			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);

			Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);

			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);

			targetDataLine.open(audioFormat);
			targetDataLine.start();

			Thread captureThread = new CaptureThread();
			captureThread.start();		
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	class CaptureThread extends Thread {

		byte tempBuffer[] = new byte[10000];

		public void run() {			
			try {
				while (true) {
					int cnt = targetDataLine.read(tempBuffer, 0,
							tempBuffer.length);
					out.write(tempBuffer);	
				}
				
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
		}
	}

}
