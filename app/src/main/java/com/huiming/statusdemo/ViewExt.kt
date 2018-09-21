package com.huiming.statusdemo

import android.support.v7.app.AppCompatActivity

/**
 * Created by kermitye
 * Date: 2018/9/20 19:32
 * Desc:
 */
/**
 * 获取状态栏高度
 */
fun AppCompatActivity.getStatusHeight(): Int {
    var height = 0
    val resourceId = applicationContext.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = applicationContext.resources.getDimensionPixelSize(resourceId)
    }
    return height
}