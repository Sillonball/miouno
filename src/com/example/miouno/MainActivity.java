package com.example.miouno;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.style.AlignmentSpan.Standard;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.miouno.MESSAGE";
	private final IntentFilter intentFilter = new IntentFilter();
	Channel mChannel;
	WifiP2pManager mManager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
     // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.activity_main);
        
        // Initialize member TextView so we can manipulate it later
        //TextView mTextView = (TextView) findViewById(R.id.text_message);
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // For the main activity, make sure the app icon in the action bar
            // does not behave as a button
            ActionBar actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(false);
        }
        //  Indicates a change in the Wi-Fi Peer-to-Peer status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        
        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }
    
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        /*if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }*/
    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        /*if (mCamera == null) {
            initializeCamera(); // Local method to handle camera init
        }*/
    }
    
    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
    }
    
    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        
        // Activity being restarted from stopped state    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   public void reproduce (View view) {
	   	Intent intent = new Intent(this, WebMusic.class);
	   	//System.out.println("hace el intent");
   		startActivity(intent);
	      
   }
   
   public void musicalocal (View view) {
	   	Intent intent = new Intent(this, localActivity.class);
	   	//System.out.println("hace el intent");
  		startActivity(intent);
	      
  }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    
    
    
}
