package com.myexercuse.myexercise.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by job on 2015/12/17.
 * 截取字符串工具
 */
public class StringDealUtil {

    public static String cut(String string){
  if (string!=null) {
      if (string.length() > 2) {
          String newstring = string.substring(0, 2) + "···";
          return newstring;
      }
      return string;
  }
       return null;
    }

    public static String cutTenString(String string){
        if(string.length()>10){
            String newstring=string.substring(0,10);
            return newstring;
        }
        return string;
    }
    public static String cutTimeString(String string){
        if(string.length()>16){
            String newstring=string.substring(0,16);
            return newstring;
        }
        return string;
    }

    public static String cutTimeStringYear(String string){
        if(string.length()>4){
            String newstring=string.substring(0,4);
            return newstring;
        }
        return string;
    }

    public static String cutTimeStringMonthDay(String string){
        if(string.length()>11){
            String newstring=string.substring(5,11);
            return newstring;
        }
        return string;
    }

    public static String cutTimeStringTime(String string){
        if(string.length()>16){
            String newstring=string.substring(11,16);
            return newstring;
        }
        return string;
    }

    public static String cutNickName(String string){
        if(string.length()>3){
            String newstring=string.substring(0,3)+"...";
            return newstring;
        }
        return string;
    }

    /**替换uuid*/
    public static String ReplaceStr(String uuid){
        String definitionuuid = uuid.replace("-", "_");
        return definitionuuid;
    }

    /**替换手机号中间四位*/
    public static String replaceBetweenStr(String replaceStr){
        String rdefinitionPhoneStr=replaceStr.substring(3,4);
        return rdefinitionPhoneStr.replaceAll(rdefinitionPhoneStr, "****");
    }

    /**截取替换后uuid后六位*/
    public static String cutUuid(String replaceStr){
            String cutuuid=replaceStr.substring(replaceStr.length() - 6, replaceStr.length());
            return cutuuid;
    }

    /**判断一个字符串是否包含另一个字符串*/
    public static boolean isContain(String s1,String s2) {
        return s1.contains(s2);
    }

    /**判断某个字符串是否包含另一个字符串中的字符*/
    public static boolean isHave(String strs,String s){
        for (int i = 0; i < strs.length()-1; i++) {
            for (int j = i+1; j < strs.length(); j++) {
                if(s.toLowerCase().contains(strs.toLowerCase().substring(i, j))){
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * String转换Map
     */
        public static Map<String,String> String2Map(String str) {
            Map<String, String> map = new HashMap<>();
            String[] arraydata = str.split("&");//按“&”将其分为字符数组
            for (int i = 0; i < arraydata.length; i++) {
                int j = arraydata[i].indexOf("=");
                map.put(arraydata[i].substring(0, j), arraydata[i].substring(j+1, arraydata[i].length()));
            }
            return map;
        }

}
