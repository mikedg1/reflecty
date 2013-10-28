package com.mikedg.java.reflecty;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class GtaClient implements Client {
	private String mIp;
	private DataOutputStream ds;

	public GtaClient(String ip) {
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
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", bos);
			byte[] imageBytes = bos.toByteArray();
			//ImageInputStream imageStream = ImageIO.createImageInputStream(img);
			//imageStream.
	        ds.writeInt(imageBytes.length);
	        ds.write(imageBytes);
	        ds.flush();
	        System.out.println("Wrote: " + imageBytes.length);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReflectyException(e);
		}
	}
}
