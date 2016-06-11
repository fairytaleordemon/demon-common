package com.yuanbosu.common.character;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randoms {

	private static final String lowercase = "abcdefghijklmnopqrstuvwxyz";
	private static final String uppercase = "ABCDEGHIJKLIMNOPQRSTUVWXYZ";
	private static final String letter = "abcdefghijklmnopqrstuvwxyzABCDEGHIJKLIMNOPQRSTUVWXYZ";
	private static final String number = "0123456789";
	private static final String lowercase_number = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final String string = "abcdefghijklmnopqrstuvwxyzABCDEGHIJKLIMNOPQRSTUVWXYZ0123456789";

	public long number(long origin, long bound) {
		return ThreadLocalRandom.current().nextLong(origin, bound);
	}

	public static String number(int length) {
		return string(number, length);
	}

	public static String letter(int length){
		return string(letter,length);
	}
	
	public static String lowercase(int length) {
		return string(lowercase, length);
	}

	public static String uppercase(int length) {
		return string(uppercase, length);
	}

	public static String lowercaseNumber(int length) {
		return string(lowercase_number, length);
	}

	public static String string(int length) {
		return string(string, length);
	}

	public static String string(String range, int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(range.length());
			sb.append(range.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000; ++i) {
			System.out.println(string(60));
		}
	}
}
