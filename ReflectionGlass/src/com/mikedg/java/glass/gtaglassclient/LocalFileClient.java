package com.mikedg.java.glass.gtaglassclient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class LocalFileClient implements Client {
	private String mIp;
	private DataOutputStream ds;

	public LocalFileClient(String ip) {
		mIp = ip;
	}

	public void setupClient() throws ReflectyException {
		try {
			Socket connection = new Socket(mIp, GtaServer.PORT);

			OutputStream os = connection.getOutputStream();
			ds = new DataOutputStream(os);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ReflectyException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReflectyException(e);
		}
	}

	public void sendBitmap(BufferedImage img) throws ReflectyException {
		try {
			File outputfile = new File("saved.png");
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReflectyException(e);
		}
	}
}
