package com.myexercuse.myexercise.util;

import android.util.Log;

/**
 * 统一处理日志־
 * baidu android log4j
 * @author tarena
 * 
 */
public class LogUtil {
	public static void i(String tag, Object msg) {
		
		Log.i(tag, String.valueOf(msg));
	}

}
