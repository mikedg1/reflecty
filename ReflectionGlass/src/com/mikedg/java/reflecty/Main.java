package com.mikedg.java.reflecty;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

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
			//Rectangle rect = new Rectangle(0, 870, 371, 208); //For my 1920x1200
			Rectangle rect = new Rectangle(0, (int)(.725 * dim.height), (int)(.1932 * dim.width), (int)(.1733 * dim.height));

			BufferedImage img = robot.createScreenCapture(rect);
		    mClient.sendBitmap(img);
		    Thread.sleep(TIME);
		}
		System.out.println("bye");
	}
}
