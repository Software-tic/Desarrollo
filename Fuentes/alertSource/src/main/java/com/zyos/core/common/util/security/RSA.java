package com.zyos.core.common.util.security;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMReader;

import com.zyos.core.common.list.BeanList;

public class RSA {



	public RSA() {

	}

	public static void main(String[] args) throws IOException {

		// KeyPair keyPair = readKeyPair(new File("private_key.pem"));
		// // if the private key is not encripted, pass can be anything.
		// Key publickey = readPublicKey(new File("public_kye.pem"));
		// Base64 base64 = new Base64();
		// String text = "123456";
		// byte[] encripted;
		// //System.out.println("input:\n" + text);
		// encripted = encrypt(keyPair.getPublic(), text);
		// // System.out.println("cipher:\n" + base64.encodeAsString(encripted));
		// // System.out.println("decrypt:\n" + decrypt(text));
	}

	/**
	 * 
	 * @author Hogar 19/08/2014 09:16:11
	 * @param pubkey
	 * @param text
	 * @return
	 */
	public static String encrypt(String text) {
		try {
			Cipher rsa;
			rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.ENCRYPT_MODE, BeanList.getPublicKey());
			byte[] utf8 = rsa.doFinal(Base64.decodeBase64(text));
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @author Hogar 19/08/2014 09:16:05
	 * @param text
	 * @return
	 */
	public static String decrypt(String text) {
		try {
			Cipher rsa;
			rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.DECRYPT_MODE, BeanList.getPrivateKey().getPrivate());
			byte[] utf8 = rsa.doFinal(Base64.decodeBase64(text));
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @author Hogar 19/08/2014 09:16:16
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */
	public static KeyPair readKeyPair(File privateKey) throws IOException {
		FileReader fileReader = new FileReader(privateKey);
		PEMReader r = new PEMReader(fileReader);
		try {
			return (KeyPair) r.readObject();
		} catch (IOException ex) {
			throw ex;
		} finally {
			r.close();
			fileReader.close();
		}
	}

	/**
	 * 
	 * @author Hogar 19/08/2014 09:16:19
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */
	public static Key readPublicKey(File privateKey) throws IOException {
		FileReader fileReader = new FileReader(privateKey);
		PEMReader r = new PEMReader(fileReader);
		try {
			return (RSAPublicKey) r.readObject();
		} catch (IOException ex) {
			throw ex;
		} finally {
			r.close();
			fileReader.close();
		}
	}
}
