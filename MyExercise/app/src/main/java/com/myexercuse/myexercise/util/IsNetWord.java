package com.myexercuse.myexercise.util;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;

public class IsNetWord {

	/* 
	 * 判断网络连接是否已开 
	 * true 已打开  false 未打开 
	 * */  
	public static boolean isConn(Context context){  
	    boolean bisConnFlag=false;  
	    ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    NetworkInfo network = conManager.getActiveNetworkInfo();  
	    if(network!=null){  
	        bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();  
	    }  
	    return bisConnFlag;  
	}  
	  
	/** 
	 * 当判断当前手机没有网络时选择是否打开网络设置 
	 * @param context 
	 */  
	public static void showNoNetWorkDlg(final Context context) {  
	    Builder builder = new Builder(context);
	    //builder.setInverseBackgroundForced(true);
	    //builder.setIcon(R.drawable.ic_launcher)         //  
	            builder.setTitle("当前网络状态")            //  
	            .setMessage("无网络").setPositiveButton("设置", new OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    // 跳转到系统的网络设置界面  
	                    Intent intent = null;  
	                    // 先判断当前系统版本  
	                    if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
	                        intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
						}else{
	                        intent = new Intent();
	                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
	                    }  
	                    context.startActivity(intent);  
	                      
	                }  
	            });
//	            .setNegativeButton("知道了", new OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						Toast.makeText(context, "亲你还没打开网络",0).show();
//						context.
//					}
//				});  

     Dialog dialog=builder.create();
     
	         dialog.show();
	         dialog.setCanceledOnTouchOutside(false);
    
	         dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
	        	   @Override
	        	   public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	        	   {
	        	   if (keyCode ==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
	        	    {
	        	     return true;
	        	    }
	        	    else
	        	    {
	        	     return false; //默认返回 false
	        	    }
	        	   }
	        	  });

	         
	}  
	public static void jumptosetting(Context context){
	    // 跳转到系统的网络设置界面
        Intent intent = null;
        // 先判断当前系统版本
        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }else{
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);
	}

	/**
	 * make true current connect service is wifi
	 * @param mContext
	 * @return
	 */
	public static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
}
