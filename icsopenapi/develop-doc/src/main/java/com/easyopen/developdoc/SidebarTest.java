package com.easyopen.developdoc;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 生成_sidebar.md文件，直接运行即可
 * @author tanghc
 */
public class SidebarTest {

	static String format = "  * [%s](files/%s)\r\n";
	static String sidebar_format = "* 文档目录\r\n\r\n%s";

	public static void main(String[] args) throws Exception {
		String path = SidebarTest.class.getClassLoader().getResource("").getPath();
		String root = path.substring(0, path.indexOf("develop-doc")) + "develop-doc";
		String fileDir = root + "/docs/files";
		File dir = new File(fileDir);
		File[] files = dir.listFiles();
		StringBuilder output = new StringBuilder();
		for (File file : files) {
			String filename = file.getName();
			String title = filename.substring(filename.indexOf("_") + 1, filename.length() - 3);
			String line = String.format(format, title, filename);
			output.append(line);
		}
		
		String sidebarContent = String.format(sidebar_format, output.toString());
		
		System.out.println(sidebarContent);
		
		String sidebarFilepath = root + "/docs/_sidebar.md";
		
		FileOutputStream out = new FileOutputStream(new File(sidebarFilepath));
		out.write(sidebarContent.getBytes());
		out.close();
	}
}
