package me.fenglu.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class JavaSouceFilePickup {
	private static List<File> javaSourceFiles;
	public static List<File> pickup(String rootPath) {
		if(null == rootPath || rootPath.equals("")) {
			return null;
		}else {
			File rootDir = new File(rootPath);
			if(rootDir.exists()) {
				javaSourceFiles = new ArrayList<File>();
				traverse(rootDir);
				return javaSourceFiles;
			}else {
				return null;
			}
		}
	}
	
	public static void traverse(File rootPath) {
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				if(pathname.isDirectory()) {
					return true;
				}else if(fileName.indexOf('.') != -1 && fileName.substring(fileName.lastIndexOf("."), fileName.length()).equalsIgnoreCase(".java")) {
					return true;
				}
				return false;
			}
		};
		for(File child : rootPath.listFiles(filter)) {
			if(child.isDirectory()) {
				traverse(child);
			}else if(child.isFile()) {
				javaSourceFiles.add(child);
			}
		}
	}
}
