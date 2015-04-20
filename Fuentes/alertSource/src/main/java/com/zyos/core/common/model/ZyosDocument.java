package com.zyos.core.common.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;

import com.zyos.core.common.util.security.MD5;

public class ZyosDocument implements Serializable {

	private int hashCode;
	private String md5;
	private String absolutePath;
	private String name;
	private String extension;
	private boolean downloadable;
	private boolean deleteable;
	private boolean file;
	private boolean join;
	private File document;

	public ZyosDocument(File f) throws Exception {
		try {
			this.absolutePath = f.getPath();
			this.name = f.getName();
			this.hashCode = f.hashCode();
			this.file = f.isFile();
			this.deleteable = f.canWrite();
			int dot = absolutePath.lastIndexOf(".");
			md5 = new MD5(hashCode).asHex();
			if (dot > 0)
				extension = absolutePath.substring(dot + 1).toLowerCase();
			File[] fl = f.listFiles();
			if (fl != null)
				downloadable = !file && f.listFiles().length > 0 ? true : false;
			else
				downloadable = false;
			document = f;
		} catch (Exception e) {
			throw e;
		}
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFile(boolean file) {
		this.file = file;
	}

	public boolean isJoin() {
		return join;
	}

	public void setJoin(boolean join) {
		this.join = join;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public boolean isDeleteable() {
		return deleteable;
	}

	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}

	public boolean isFile() {
		return file;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	/**
	 * Method for getting the zyosfile like a File of java
	 * 
	 * @return
	 */
	public File getLikeFile() {
		return document;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public boolean isDownloadable() {
		return downloadable;
	}

	public void setDownloadable(boolean downloadable) {
		this.downloadable = downloadable;
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists())
			destFile.createNewFile();

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			long count = 0;
			long size = source.size();
			while ((count += destination.transferFrom(source, count, size - count)) < size);
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
}
