package com.example.miouno;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MusicaActivity extends Activity implements OnCompletionListener {

	AssetManager manager;
	MediaPlayer player;
	Button reproductor;
	
	@Override
	public void onCreate(Bundle savedInstanceState)  {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musica);
		/*WebView myWebView = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl("http://muzare.com/prototipo/mainstation");*/
		reproductor = (Button)findViewById(R.id.button1);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		HttpURLConnection conn = null;
		InputStream is;   	
		
		reproductor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
				    public void run() {
				    	String buffer="";
				    	// Conexion con la URL
						HttpGet httpGet = new HttpGet("http://muzare.com/prototipo/mainstation");
						System.out.println("despues");
						HttpClient httpclient = new DefaultHttpClient();
						System.out.println("despues2");
					    HttpResponse response;
						try {
							response = httpclient.execute(httpGet);						
							System.out.println("despues3");
							buffer = readFromBuffer(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
							String[] palabras = buffer.split("\"");
							System.out.println(palabras[0]);
							System.out.println(palabras[1]);
							System.out.println(palabras[2]);
							Long offset = Long.valueOf(palabras[2].substring(2, 12));
							System.out.println(offset);
							AssetFileDescriptor descriptor = manager.openFd(palabras[1]);
							player.setDataSource(descriptor.getFileDescriptor(), offset, descriptor.getLength());
							player.prepare();
							player.start();
							
						} catch (Exception e) {
							e.printStackTrace();
						}						
						System.out.println("fuera");
				    }
				}).start();				
			}
		});
		player.setOnCompletionListener(this);
			/*is = (InputStream) url.getContent();
			System.out.println("hace el inputstream");		
			for (int i=0; is.available()>0 ;i++){
				buffer= buffer.concat(String.valueOf(is.read()));
			}*/		    
			
			
		
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		
		player.stop();
	}
	
	private String readFromBuffer(BufferedReader br){
	    StringBuilder text = new StringBuilder();
	    try{
	        String line;
	        while ((line = br.readLine()) != null) {
	            text.append(line);
	            text.append("\n");
	        } 
	    } catch (IOException e) { 
	        e.printStackTrace();
	        // tratar excepción!!!
	    }
	    return text.toString();
	}	
		
	
}