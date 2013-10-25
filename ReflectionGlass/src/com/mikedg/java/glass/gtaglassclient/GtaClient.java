package com.mikedg.java.glass.gtaglassclient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class GtaClient {
	private String mIp;

	public GtaClient(String ip) {
		mIp = ip;
	}
	
	public void setupClient() throws UnknownHostException, IOException {
        Socket connection = new Socket(mIp, GtaServer.PORT);
        
        OutputStream os = connection.getOutputStream();
        ds = new DataOutputStream(os);
	}
	DataOutputStream ds;

	public void sendBitmap(BufferedImage img) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", bos);
		byte[] imageBytes = bos.toByteArray();
		//ImageInputStream imageStream = ImageIO.createImageInputStream(img);
		//imageStream.
        ds.writeInt(imageBytes.length);
        ds.write(imageBytes);
        ds.flush();
        System.out.println("Wrote: " + imageBytes.length);
	}
}
