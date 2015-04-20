package com.zyos.core.common.util.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZyosZIP {

	/**
	 * Method for compress a file or a lot of files and create a ZIP file
	 * 
	 * @param outFilename
	 *            path output file
	 * @param filenameList
	 *            path list with the files to compress
	 * @return boolean true if compress or false else not
	 * @throws IOException 
	 */
	public static boolean CreateZIPFile(String outFilename, String filePath,
			String... filenameList) throws IOException {
		try {
			if (filenameList != null && filenameList.length > 0 && outFilename != null && !outFilename.isEmpty()) {
				
				FileOutputStream fout = new FileOutputStream(outFilename);
				ZipOutputStream zout = new ZipOutputStream(fout);
				File fileSource = new File(filePath);
				addDirectory(zout, fileSource, fileSource.getName());
				zout.close();
				
				System.out.println("Zip file has been created!");
				return true;
			}
			return false;
			
		} catch (IOException e) {
			throw e;
		}
	}

	private static void addDirectory(ZipOutputStream zout, File fileSource, String parent) {

		File[] files = fileSource.listFiles();
		System.out.println("Adding directory " + fileSource.getName());

		for (int i = 0; i < files.length; i++) {
			// if the file is directory, call the function recursively
			if (files[i].isDirectory()) {
				addDirectory(zout, files[i], parent + File.separator + files[i].getName());
				continue;
			}

			/*
			 * its file and not directory, so add it to the zip file
			 */
			try {
				System.out.println("Adding file " + files[i].getName());

				// create byte buffer
				byte[] buffer = new byte[1024];

				// create object of FileInputStream
				FileInputStream fin = new FileInputStream(files[i]);
				zout.putNextEntry(new ZipEntry(parent + File.separator + files[i].getName()));

				int length;

				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
				zout.closeEntry();
				fin.close();

			} catch (IOException ioe) {
				System.out.println("IOException :" + ioe);
			}
		}

	}
}
