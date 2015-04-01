package me.fenglu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import me.fenglu.gui.FileEncodingConverterMainFrame;

import org.mozilla.universalchardet.UniversalDetector;

public class FileEncodingConverter {
	
	public static final int BUFSIZE = 1024;
	
	public boolean toUTF8(List<File> javaSourceFiles) {
		return this.toUTF8(javaSourceFiles, null);
	}
	public boolean toUTF8(List<File> javaSourceFiles, FileEncodingConverterMainFrame frame) {
		for (File file : javaSourceFiles) {
			String encoding  = detectEncoding(file);
			if(null == encoding || encoding.equalsIgnoreCase("UTF-8")) {
				if(frame != null) {
					frame.displayInfo(file.getAbsolutePath()+"已经是UTF-8编码，无需转换。");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				continue;
			}else{
				String fileName = file.getAbsolutePath();
				StringBuilder tempFileName = new StringBuilder(fileName);
				tempFileName.insert(fileName.lastIndexOf('.'), "_temp");
				File tempFile = new File(tempFileName.toString());
				//BufferedReader br = null;
				//BufferedWriter bw = null;
				RandomAccessFile randomAccess = null;
				RandomAccessFile randomAccessTemp = null;
				try {
					/*FileInputStream fis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(fis, encoding);
					br = new BufferedReader(isr);
					
					FileOutputStream fos = new FileOutputStream(tempFile, true);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
					bw = new BufferedWriter(osw);
					char[] cbuf = new char[1024];
					while(br.read(cbuf) != -1) {
						bw.write(cbuf);
						cbuf = new char[1024];
					}*/
					randomAccess = new RandomAccessFile(file, "r");
					randomAccessTemp = new RandomAccessFile(tempFile, "rw");
					long length = randomAccess.length();
					byte[] buf = null;
					if(length > BUFSIZE) {
						buf = new byte[BUFSIZE];
					}else {
						buf = new byte[(int)length];
					}
					while(randomAccess.read(buf) != -1) {
						String str = new String(buf, "GB18030");
						randomAccessTemp.write(str.getBytes("UTF-8"));
						long offset = randomAccess.getFilePointer();
						long overlength = BUFSIZE-offset;
						if(overlength > BUFSIZE) {
							buf = new byte[BUFSIZE];
						}else {
							buf = new byte[(int) overlength];
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if(randomAccess != null) {
							randomAccess.close();
						}
						if(randomAccessTemp != null) {
							randomAccessTemp.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(file.delete()) {
						tempFile.renameTo(new File(fileName));
						//System.out.println(file.getName() + "成功转换。");
						frame.displayInfo(file.getAbsoluteFile() + "成功转换。");
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						System.out.println("error");
					}
				}
			}

		}
		return false;
	}

	public String detectEncoding(File file) {
		byte[] buf = new byte[4096];
		String encoding = "";
		FileInputStream fis = null;
		UniversalDetector detector = null;
		try {
			fis = new FileInputStream(file);
			detector = new UniversalDetector(null);
			int nread;
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
			detector.dataEnd();
			encoding = detector.getDetectedCharset();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(detector != null) {
				detector.reset();
			}
		}
		return encoding;
	}
	
	public static void main(String[] args) {
		new FileEncodingConverter().toUTF8(JavaSouceFilePickup.pickup("/home/king/Desktop/project/"));
	}
}
