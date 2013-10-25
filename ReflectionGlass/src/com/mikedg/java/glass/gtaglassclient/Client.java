package com.mikedg.java.glass.gtaglassclient;

import java.awt.image.BufferedImage;

public interface Client {
	public void setupClient() throws ReflectyException;
	public void sendBitmap(BufferedImage img) throws ReflectyException;
}
