package com.techflow.ws.sys.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Base64;


/*
 * Tamanho da chave AES: 16, 24 ou 32 bytes
 * InitVector: 16 bytes

 */
public class EncryptUtils {
	
	public static String decrypt(String data) {
		if(data == null || data == "" || data.length() < 50) return "";
		try {
			return decrypt(data.substring(32), data.substring(0,16), data.substring(16,32));
		}
		catch(Exception ex) {
			return "";
		}
	}
	
	public static String encrypt(String data) {
		if(data == null || data == "") return "";
		try {
			String sKey = Utils.generateRandomString(16);
			String ivKey = Utils.generateRandomString(16);
			String encryptedData = encrypt(data, ivKey, sKey);
			return sKey + ivKey + encryptedData;
		}
		catch(Exception ex) {
			return "";
		}
	}
	
	public static String encrypt(String data, String ivKey, String sKey)
			throws Exception {
		IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

		byte[] encrypted = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}

	
	public static String decrypt(String encryptedData, String ivKey, String sKey) throws Exception {
		IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		return new String(original);
	}
	
	public static String md5(String str) {
		String hash = DigestUtils.md5(str).toString();
		return hash;
	}
	
	public static String getMD5(String input) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Add the bytes of the input string to the digest
            md.update(input.getBytes());
            
            // Get the hash's bytes
            byte[] bytes = md.digest();
            
            // This will contain hash as hexadecimal
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            // Return the complete hash
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}