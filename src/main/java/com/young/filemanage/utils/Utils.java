package com.young.filemanage.utils;

import java.util.UUID;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 20:10
 */
public class Utils {
    // 获取
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
