package com.mikedg.java.glass.gtaglassclient;

import java.awt.AWTException;
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

	public static final void main(String[] args) throws AWTException, IOException, InterruptedException {
		System.out.println("hi");
		
		Main main = new Main();
	}

	private GtaClient mClient;
	
	public Main() throws UnknownHostException, IOException, InterruptedException, AWTException {
		GtaServer server = new GtaServer(new SavingImageByteHandler());
		//server.setupServer();
		Thread.sleep(2000); //Make sure the server is waiting
		
		//mClient = new GtaClient("127.0.0.1");
//		mClient = new GtaClient("172.25.7.36");
		mClient = new GtaClient("192.168.43.100");
//		mClient = new GtaClient("192.168.43.100");

		mClient.setupClient();
		
		doShots();
	}

	private void doShots() throws AWTException, IOException, InterruptedException {

		Robot robot = new Robot();
		//Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(0, 870, 371, 208);

		for (int i = 0; i > -1; i++) { //FOREVER
			BufferedImage img = robot.createScreenCapture(rect);
		    File outputfile = new File("saved"+i+".png");
		    //ImageIO.write(img, "png", outputfile);
		    mClient.sendBitmap(img);
		    //Thread.sleep(5000);
		    Thread.sleep(TIME); //4x a second
		}
		System.out.println("bye");
	}
}
