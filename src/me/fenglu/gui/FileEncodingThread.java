package me.fenglu.gui;

import me.fenglu.util.FileEncodingConverter;
import me.fenglu.util.JavaSouceFilePickup;

public class FileEncodingThread implements Runnable {

	private FileEncodingConverter converter;
	private FileEncodingConverterMainFrame frame;
	
	@Override
	public void run() {
		converter.toUTF8(JavaSouceFilePickup.pickup(frame.getFile().getText()), frame);
	}
	
	public FileEncodingConverter getConverter() {
		return converter;
	}
	public void setConverter(FileEncodingConverter converter) {
		this.converter = converter;
	}

	public FileEncodingConverterMainFrame getFrame() {
		return frame;
	}

	public void setFrame(FileEncodingConverterMainFrame frame) {
		this.frame = frame;
	}

}
