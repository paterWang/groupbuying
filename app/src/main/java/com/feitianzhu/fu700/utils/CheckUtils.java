package com.feitianzhu.fu700.utils;

import android.text.TextUtils;

/**
 * Created by Vya on 2017/9/19.
 * 检测是否为空
 */

public class CheckUtils {

    public static boolean isEmpty(String... args){
        for(int i=0;i<args.length;i++){
            if(TextUtils.isEmpty(args[i])){
                return true;
            }
        }
        return false;
    }

}
