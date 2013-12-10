package com.mikedg.java.reflecty;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GtaServer {
	public static final int PORT = 6666;
	private Thread mServerThread;
	private ImageByteHandler mHandler;
	private ServerSocket mServerSocket;
	private Socket mConnection;
	
	public GtaServer(ImageByteHandler handler) {
		mHandler = handler;
	}

	public void setupServer() {
		Runnable server = new Runnable() {
			@Override
			public void run() {
				mServerSocket = null;
				while (true) { // FIXME: should have a way out thats not killing
								// the app
					try {
						mServerSocket = new ServerSocket(PORT);
						mConnection = mServerSocket.accept();

						InputStream is = mConnection.getInputStream();
						DataInputStream ds = new DataInputStream(
								new BufferedInputStream(is));
						while (is.available() > -1) {
							int size = ds.readInt(); // num of bytes here
							System.out.println("Reading: " + size);

							// how the hell does this happen
							// 09-27 07:20:27.964: I/System.out(16960): trying
							// to read 1
							// 09-27 07:20:32.112: I/System.out(16960): Read
							// len: 2071
							// 09-27 07:20:32.120: I/System.out(16960): Read 1
							// 09-27 07:20:32.120: I/System.out(16960): trying
							// to read 1
							// 09-27 07:20:32.120: D/skia(16960): ---
							// SkImageDecoder::Factory returned null
							// 09-27 07:20:32.128: I/System.out(16960): Read
							// len: -1320877688
							// 09-27 07:20:32.128: I/System.out(16960): Wtf size
							// is -1320877688
							// 09-27 07:20:32.128: I/System.out(16960): trying
							// to read 1
							// 09-27 07:20:32.128: I/System.out(16960): Read
							// len: 1553386640
							// I think this is happening cause I i had two
							// monitors hooked up and when i do that my primary
							// mnitor gets a different effective resolution
							// Seems to be similar issue with double monitor and
							// screen saver
							if (size > 0) {
								byte[] buffer = new byte[size];
								int i = 0;
								while (i != size) {
									int count = ds.read(buffer, i, size - i);
									System.out.println("Count: " + count);
									i += count;
									System.out.println("Total i: " + i);
								}
								mHandler.doSomethingWithImageBytes(buffer);
							} else {
								System.out.println("****Wtf size is:" + size);
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if (mServerSocket != null) {
							try {
								mServerSocket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				}
			}
		};
		mServerThread = new Thread(server);
		mServerThread.start();
	}

	public void stop() {
		try {
			mServerSocket.close();
			mConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
