package com.mikedg.java.reflecty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SavingImageByteHandler implements ImageByteHandler {

	@Override
	public void doSomethingWithImageBytes(byte[] buffer) throws IOException {
		writeToFile(buffer);
	}

	private void writeToFile(byte[] buffer) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("img.png"));
		fos.write(buffer);
		fos.close();
	}
}
