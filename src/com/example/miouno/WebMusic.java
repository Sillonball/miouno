package com.example.miouno;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WebMusic extends Activity {

	private TextView txtRequest;
	private MediaPlayer mediaPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = View.inflate(getApplicationContext(), R.layout.activity_musica,null);
		setContentView(v);

		Button b = (Button) v.findViewById(R.id.button1);
		b.setOnClickListener(testPlay_Button);
		b = (Button) v.findViewById(R.id.button2);
		b.setOnClickListener(testStop_Button);
		txtRequest = (TextView) v.findViewById(R.id.editText1);
	}


	private OnClickListener testStop_Button = new OnClickListener() {
		public void onClick(View v) {
			if(mediaPlayer == null)
				return;
			mediaPlayer.stop();
		}
	};

	
	private OnClickListener testPlay_Button = new OnClickListener() {
		public void onClick(View v) {
				try {
					//get the string XML from muzare
					URL url = new URL("http://muzare.com/prototipo/mainstation");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					InputStream stream = connection.getInputStream();
					// go easy on the device memory
					byte[] buf = new byte[1024];
					String value = "";
					//read all bytes and add it to a string variable
					while(stream.read(buf) != -1){
						value += new String(buf, "UTF8");
					}
					//create the JSON object
					JSONObject j = new JSONObject(value);
					// get the stream URL and create the petition string
					value = j.get("stream_url").toString();
					//value = value + "?client_id=" + getString(R.string.app_name);
					txtRequest.setText("");
					RunDownload(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		};

	
		private void RunDownload(final String surl) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
					URL url = new URL(surl);
						URLConnection connection = url.openConnection();
						connection.connect();
						// this will be useful so that you can show a typical 0-100%
						// progress bar
						int fileLength = connection.getContentLength();

						// download the file from web, reading the input stream
						InputStream input = new BufferedInputStream(url.openStream());
						//create temporal file mp3 in cache path
						File downloadingMediaFile = new File(getBaseContext().getCacheDir(), "down12541loadingMedia_.mp3");
						//class used to write in the temporal mp3 file
						OutputStream output = new FileOutputStream(downloadingMediaFile);
						//read olny 1 mb to go easy on the phone
						byte data[] = new byte[1024];
						long total = 0;
						int count;

						while ((count = input.read(data)) != -1) {
							total += count;
							// publishing the progress....
							final long copy_total = total;
							final int copy_fileLength = fileLength;
							//call main thread
							runOnUiThread(new Runnable() {
								public void run() {

									long porcentaje = copy_total * 100
											/ copy_fileLength;
									txtRequest.setText(String.valueOf(porcentaje));
									}
								});

							output.write(data, 0, count);
							}
							//we’re done, clean up memory
							output.flush();
							output.close();
							input.close();
							//call main thread to play music
							runOnUiThread(new Runnable() {
								public void run() {
									RunPlayer();
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				(new Thread(r)).start();
			}

		
		private void RunPlayer() {
			try {
		// if media player is already on use delete
				if (mediaPlayer != null) {
					mediaPlayer.release();
				}
				//read the file info from cache
				File file = new File(getBaseContext().getCacheDir(),
		"down12541loadingMedia_.mp3");
				FileInputStream fin = new FileInputStream(file);
				//create and play the media player
				mediaPlayer = new MediaPlayer();
				Log.w("RunPlayer", file.getAbsolutePath());
				mediaPlayer.setDataSource(fin.getFD());
				mediaPlayer.setLooping(true);
				mediaPlayer.setVolume(100, 100);
				mediaPlayer.prepare();
				mediaPlayer.start();
			} catch (Exception e) {
				Log.w("Error RunPlay2", e.getMessage());
			}
		}

	
}
