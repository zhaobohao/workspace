package com.easyopen.sdk;

import java.io.File;

/**
 * @author tanghc
 */
public class SidebarTest {

	static String fileDir = "E:/workspace-git/easyopen/develop-doc/docs/files";
	static String format = "  * [%s](files/%s)";

	public static void main(String[] args) {
		File dir = new File(fileDir);
		File[] files = dir.listFiles();
		for (File file : files) {
			String filename = file.getName();
			String title = filename.substring(filename.indexOf("_") + 1, filename.length() - 3);
			String out = String.format(format, title, filename);
			System.out.println(out);
		}
	}
}
