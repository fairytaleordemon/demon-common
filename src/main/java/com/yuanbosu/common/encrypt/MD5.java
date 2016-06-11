package com.yuanbosu.common.encrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {

	public static String hex(String input) {
		return DigestUtils.md5Hex(input);
	}

	public static String hex(File input) throws IOException {
		
		return DigestUtils.md5Hex(Files.readAllBytes(input.toPath()));
	}

	public static void main(String[] args) throws Exception {
//		File input = new File("/Users/zhangjie/Downloads/css.mp4");
//
//		Long tm1 = Long.valueOf(System.currentTimeMillis());
//		String md5 = hex(input);
//		Long tm2 = Long.valueOf(System.currentTimeMillis());
//		System.out.println(tm2.longValue() - tm1.longValue() + ":" + md5);
		
		
		 String aaa = DigestUtils.sha1Hex("abcde");
		 System.out.println(aaa);
	}
	
	
}
