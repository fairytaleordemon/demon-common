package com.yuanbosu.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

public class AES {

//	private static final String KEY_ALGORITHM = "AES";
//	  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
//	  private static final String charsetName = "UTF-8";

	  public static byte[] initSecretKey()
	  {
	    KeyGenerator kg = null;
	    try {
	      kg = KeyGenerator.getInstance("AES");
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	      return new byte[0];
	    }

	    kg.init(128);

	    SecretKey secretKey = kg.generateKey();
	    return secretKey.getEncoded();
	  }

	  private static Key toKey(byte[] key)
	  {
	    return new SecretKeySpec(key, "AES");
	  }

	  public static String encryptBase64(String data, String key)
	    throws Exception
	  {
	    return Base64.encodeBase64String(encrypt(data, key));
	  }

	  public static String encryptHex(String data, String key) throws Exception {
	    return Hex.encodeHexString(encrypt(data, key));
	  }

	  public static byte[] encrypt(String data, String key) throws UnsupportedEncodingException, Exception {
	    key = keyTo16(key);
	    return encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
	  }

	  public static byte[] encrypt(byte[] data, Key key)
	    throws Exception
	  {
	    return encrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] encrypt(byte[] data, byte[] key)
	    throws Exception
	  {
	    return encrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
	    throws Exception
	  {
	    Key k = toKey(key);
	    return encrypt(data, k, cipherAlgorithm);
	  }

	  public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
	    throws Exception
	  {
	    Cipher cipher = Cipher.getInstance(cipherAlgorithm);

	    cipher.init(1, key);

	    return cipher.doFinal(data);
	  }

	  public static String decryptBase64(String data, String key)
	    throws Exception
	  {
	    key = keyTo16(key);
	    return new String(decrypt(Base64.decodeBase64(data.getBytes("UTF-8")), key.getBytes("UTF-8")));
	  }

	  public static String decryptHex(String data, String key) throws Exception {
	    key = keyTo16(key);
	    return new String(decrypt(Hex.decodeHex(data.toCharArray()), key.getBytes("UTF-8")));
	  }

	  public static byte[] decrypt(byte[] data, byte[] key)
	    throws Exception
	  {
	    return decrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] decrypt(byte[] data, Key key)
	    throws Exception
	  {
	    return decrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm)
	    throws Exception
	  {
	    Key k = toKey(key);
	    return decrypt(data, k, cipherAlgorithm);
	  }

	  public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
	    throws Exception
	  {
	    Cipher cipher = Cipher.getInstance(cipherAlgorithm);

	    cipher.init(2, key);

	    return cipher.doFinal(data);
	  }

	  private static String showByteArray(byte[] data) {
	    if (null == data) {
	      return null;
	    }
	    StringBuilder sb = new StringBuilder("{");
	    for (byte b : data) {
	      sb.append(b).append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append("}");
	    return sb.toString();
	  }

	  private static String keyTo16(String key) {
	    key = StringUtils.trimToEmpty(key);
	    key = StringUtils.left(key, 16);

	    while (16 - key.length() > 0) {
	      key = new StringBuilder().append(key).append("0").toString();
	    }

	    return key;
	  }

	  public static void main(String[] args)
	    throws Exception
	  {
	    String passwd = "zhangjie";
	    byte[] key = passwd.getBytes("UTF-8");
	    System.out.println(new StringBuilder().append("key：").append(showByteArray(key)).toString());
	    System.out.println(new StringBuilder().append("key：").append(new String(key)).toString());

	    String data = "AES ！";

	    String keyString = new String(key);
	    System.out.println(keyString);
	    String encryptdBase64 = encryptBase64(data, keyString);
	    String encryptdHex = encryptHex(data, keyString);
	    System.out.println(encryptdBase64);
	    System.out.println(encryptdHex);
	    System.out.println(decryptBase64(encryptdBase64, keyString));
	    System.out.println(decryptHex(encryptdHex, keyString));
	  }
}
