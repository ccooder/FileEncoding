package me.fenglu.util;

import java.io.UnsupportedEncodingException;

public class TestCharset {
	
	public void testCharset() throws Exception{
		String CN = "中文CN";
		String EN = "CN";
		
		System.out.println(CN.getBytes("GB18030").length);
		System.out.println(EN.getBytes("GB18030").length);
		
		System.out.println(CN.getBytes().length);
		System.out.println(EN.getBytes().length);
		
		System.out.println("UTF8========");
		byte[] utf8 = CN.getBytes();
		for(byte b : utf8) {
			System.out.print(b);
		}
		System.out.println();
		
		
		System.out.println("GB18030========");
		byte[] gb = CN.getBytes("GB18030");
		for(byte b : gb) {
			System.out.print(b);
		}
		System.out.println();
		byte[] utf = new byte[]{-28, -72, -83, -26, -106, -121, 67, 78};
		for(byte b : utf) {
			System.out.print(b);
		}
		System.out.println(new String(utf));
		
	}
	
	public static void main(String[] args) {
		try {
			new TestCharset().testCharset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
