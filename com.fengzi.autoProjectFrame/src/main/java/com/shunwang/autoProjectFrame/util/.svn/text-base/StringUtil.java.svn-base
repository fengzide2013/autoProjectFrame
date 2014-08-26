/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shunwang.autoProjectFrame.util;

import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class StringUtil {
    public static String convertPointToSlash(String str){
        return str.replaceAll("\\.", "\\\\");
    }
    public static String convertStr(String temp){
    	char[] charArray = temp.toLowerCase().toCharArray();
		int length = temp.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if ('_' == temp.charAt(i)&&i!=length-1) {
				sb.append(new String("" + charArray[i + 1]).toUpperCase());
				i++;
			} else {
				sb.append(charArray[i]);
			}
		}
		return sb.toString();
    }
    @Test
    public void test(){
    	String str="com.shunwang.projectName";
    	System.out.println(convertPointToSlash(str));
    }
}
