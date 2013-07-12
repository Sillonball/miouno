package com.example.miouno;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class localActivity extends Activity implements OnCompletionListener{
	
	MediaPlayer player;
	Button parar;
	FragmentManager fragment;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localmusica);
		parar = (Button) this.findViewById(R.id.button1);
		parar.setOnClickListener(testStop_Button);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		AssetManager manager = this.getAssets();
		player = new MediaPlayer();
		try{
			final String[] archivos = manager.list("");
			
			//String[] filtro= new String[10];
			//Seleccion del archivo a reproducir
			/*for (int j=0; j<archivos.length;j++){
				System.out.println(archivos[j]);
				if (archivos[j].contains(".mp3")){
					System.out.println(archivos[j]);
					if (j==0)
						filtro[0]= archivos[j];
					filtro[filtro.length]= archivos[j];
				}
			}
			for (int i=0;i<archivos.length;i++){
				System.out.println(filtro[i]);
			}*/
			Dialog seleccion= crearDialogoSeleccion(archivos);
			AssetFileDescriptor descriptor = manager.openFd(seleccion.toString());
			player.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
			player.prepare();
			player.start();
			player.setOnCompletionListener(this);
		}catch(Exception e){}
		
	}
	
	
	private Dialog crearDialogoSeleccion(final String[] archivos) {
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Selección");
	    builder.setItems(archivos, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int archivo) {
	            Log.i("Dialogos", "Opción elegida: " + archivos[archivo]);
	        }
	    });
	 
	    return builder.create();
	}
	
	private OnClickListener testStop_Button = new OnClickListener() {
		public void onClick(View v) {
			if(player == null)
				return;
			player.stop();
		}
	};
	
	
	@Override
	public void onCompletion(MediaPlayer arg0) {
		player.stop();
		
	}

	
	
}
