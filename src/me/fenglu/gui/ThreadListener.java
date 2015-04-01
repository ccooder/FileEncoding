package me.fenglu.gui;

public class ThreadListener implements Runnable {
	private Thread t;
	private FileEncodingConverterMainFrame frame;
	@Override
	public void run() {
		while(t.isAlive()) {
			
		}
		frame.done();
	}
	public Thread getT() {
		return t;
	}
	public void setT(Thread t) {
		this.t = t;
	}
	public FileEncodingConverterMainFrame getFrame() {
		return frame;
	}
	public void setFrame(FileEncodingConverterMainFrame frame) {
		this.frame = frame;
	}

}
