package com.zyos.core.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.zyos.core.common.controller.ErrorNotificacion;

public class ZyosFile {
	/**
	 * Method for coping recursively a folder
	 * 
	 * @param srcDir
	 * @param dstDir
	 * @throws IOException
	 */
	public static void copyDirectory(File srcDir, File dstDir)
			throws IOException {
		if (srcDir.isDirectory()) {
			if (!dstDir.exists()) {
				dstDir.mkdir();
			}

			String[] children = srcDir.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(srcDir, children[i]), new File(dstDir,
						children[i]));
			}
		} else {
			copy(srcDir, dstDir);
		}
	}

	/**
	 * Method for coping a only file
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
	/**
	 * Method for deleting a file after to create a report
	 * 
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		try {
			File f = new File(fileName);

			// Make sure the file or directory exists and isn't write protected
			if (!f.exists())
				throw new IllegalArgumentException(
						"Delete: no such file or directory: " + fileName);

			if (!f.canWrite())
				throw new IllegalArgumentException("Delete: write protected: "
						+ fileName);

			// If it is a directory, make sure it is empty
			if (f.isDirectory()) {
				String[] files = f.list();
				if (files.length > 0)
					throw new IllegalArgumentException(
							"Delete: directory not empty: " + fileName);
			}
			// Attempt to delete it
			f.delete();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "deleteFile");
		}
	}
	
}
