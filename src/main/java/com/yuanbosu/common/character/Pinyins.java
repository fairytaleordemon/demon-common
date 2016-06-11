package com.yuanbosu.common.character;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyins {

	HanyuPinyinOutputFormat format = null;

	  public Pinyins()
	  {
	    this.format = new HanyuPinyinOutputFormat();
	    this.format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	    this.format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	  }

	  public String toPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
	    return toPinYin(str, "", Type.UPPERCASE);
	  }

	  public String toPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
	    return toPinYin(str, spera, Type.UPPERCASE);
	  }

	  public String toPinYin(String str, String spera, Type type)
	    throws BadHanyuPinyinOutputFormatCombination
	  {
	    if ((str == null) || (str.trim().length() == 0))
	      return "";
	    if (type == Type.UPPERCASE)
	      this.format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	    else {
	      this.format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	    }
	    String py = "";
	    String temp = "";

	    for (int i = 0; i < str.length(); i++) {
	      char c = str.charAt(i);
	      if (c <= '') {
	        py = new StringBuilder().append(py).append(c).toString();
	      } else {
	        String[] t = PinyinHelper.toHanyuPinyinStringArray(c, this.format);
	        if (t == null) {
	          py = new StringBuilder().append(py).append(c).toString();
	        } else {
	          temp = t[0];
	          if (type == Type.FIRSTUPPER)
	            temp = new StringBuilder().append(t[0].toUpperCase().charAt(0)).append(temp.substring(1)).toString();
	          py = new StringBuilder().append(py).append(temp).append(i == str.length() - 1 ? "" : spera).toString();
	        }
	      }
	    }
	    return py.trim();
	  }

	  public static void main(String[] args) throws Exception {
	    Pinyins py = new Pinyins();

	    System.out.println(py.toPinYin("音乐Adam是1个好人", "_", Type.LOWERCASE));
	  }

	  public static enum Type
	  {
	    UPPERCASE, 
	    LOWERCASE, 
	    FIRSTUPPER;
	  }
}
