package com.mattdamon.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.mattdamon.core.common.GlobalVariables;

/**
 * 
 * FileUtiler
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 17, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class FileUtiler implements GlobalVariables {

	/**
	 * 输入文件，兼容linux 与 window
	 * 
	 * @param is
	 * @param path
	 * @param filename
	 * @throws Exception
	 */
	public synchronized static void outputFile(InputStream is, String path,
			String filename) throws Exception {
		FileOutputStream fs = null;

		String[] folders = path.split(DIR_SEPARATOR);
		folders[1] = DIR_SEPARATOR + folders[1];

		String dir = "";
		boolean succ = true;
		for (int index = 1; succ && index < folders.length; index++) {
			if (index == 1) {
				dir = folders[index];
			} else {
				dir = dir + DIR_SEPARATOR + folders[index];
			}
			File file = new File(dir);
			if (!file.exists()) {
				succ = file.mkdir();
			}
		}

		if (succ == false) {
			// Something wrong in generating output folder.
			System.err.println("Unable create folder for " + path);
			return;
		}

		fs = new FileOutputStream(path + DIR_SEPARATOR + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = is.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}

		if (fs != null) {
			fs.close();
		}
		if (is != null) {
			is.close();
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param path
	 *            被删除文件的文件名
	 */
	public synchronized static void deleteFile(String path) throws Exception {
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param path
	 *            被删除目录的文件路径
	 */
	public synchronized static void deleteDirectory(String path)
			throws Exception {
		File dirFile = new File(path);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				deleteFile(files[i].getAbsolutePath());
			} // 删除子目录
			else {
				deleteDirectory(files[i].getAbsolutePath());
			}
		}
		// 删除当前目录
		dirFile.delete();
	}
}
