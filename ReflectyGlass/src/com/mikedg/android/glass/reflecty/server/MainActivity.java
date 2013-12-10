package com.mikedg.android.glass.reflecty.server;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikedg.java.reflecty.GtaServer;
import com.mikedg.java.reflecty.ImageByteHandler;

public class MainActivity extends Activity {

	private GtaServer mServer;
	private ImageView mDisplay;
	private TextView mIpAddressTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupServer();
		mDisplay = (ImageView)findViewById(R.id.imageView1);
		mIpAddressTextView = (TextView)findViewById(R.id.textView1);
		showIpAddress();
	}

	private void showIpAddress() {
		WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		String ipAddress = Formatter.formatIpAddress(ip); //FIXME: this is deprecated
		mIpAddressTextView.setText(ipAddress);
	}

	private void setupServer() {
		mServer = new GtaServer(new DisplayImageByteHandler());
		mServer.setupServer();
	}

	//FIXME: on pause, tear down the server
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		mServer.stop();
		//FIXME: don't have time to debug this, so
		System.exit(0);
	}

	private class DisplayImageByteHandler implements ImageByteHandler {
		@Override
		public void doSomethingWithImageBytes(final byte[] buffer) throws IOException {
			runOnUiThread(new Runnable() {
				private Bitmap oldBm = null;
				public void run() {
					L.d("do something with bitmap");
					if (oldBm != null) {
						oldBm.recycle();
					}
					Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
					mDisplay.setImageBitmap(bm);
					
					oldBm = bm;
				}
			});
		}
	}
}
