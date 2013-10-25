package com.mikedg.java.reflecty;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;


public class Main {
	//Need to slow down to do vignettes
	//private static final long TIME = 1000;
	private static final long TIME = 200;

	public static final void main(String[] args) throws AWTException, ReflectyException, InterruptedException {
		System.out.println("hi");
		
		Main main = new Main();
	}

	private GtaClient mClient;
	
	public Main() throws ReflectyException, InterruptedException, AWTException {
//		runLocally();
//		mClient = new GtaClient("172.25.7.36");
		mClient = new GtaClient("192.168.43.100");
//		mClient = new GtaClient("192.168.43.100");

		mClient.setupClient();
		
		doShots();
	}

	private void runLocally() throws InterruptedException {
		GtaServer server = new GtaServer(new SavingImageByteHandler());
		server.setupServer();
		Thread.sleep(2000); //Make sure the server is waiting
		
		mClient = new GtaClient("127.0.0.1");
	}

	
	private void doShots() throws AWTException, ReflectyException, InterruptedException {
		Robot robot = new Robot();

		for (int i = 0; i > -1; i++) { //FOREVER
			//Need this here in case it changes
			Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			//Rectangle rect = new Rectangle(0, 870, 371, 208);
			Rectangle rect = new Rectangle(0, (int)(870.0/1200.0 * dim.height), (int)(371.0/1920.0 * dim.width), (int)(208.0/1200.0 * dim.height)); //FIXME: might be wrong calcs

			BufferedImage img = robot.createScreenCapture(rect);
		    mClient.sendBitmap(img);
		    Thread.sleep(TIME); //4x a second
		}
		System.out.println("bye");
	}
}
