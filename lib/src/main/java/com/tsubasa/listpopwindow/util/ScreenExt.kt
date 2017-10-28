package com.tsubasa.listpopwindow.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/***
 * <br> Project ListPopWindow
 * <br> Package com.tsubasa.listpopwindow.util
 * <br> Description 屏幕的相关扩展
 * <br> Version 1.0
 * <br> Author Tsubasa
 */
fun Context.getScreenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()// 创建了一张白纸
    windowManager.defaultDisplay.getMetrics(dm)// 给白纸设置宽高
    return dm.widthPixels
}

fun Context.getScreenHeight(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()// 创建了一张白纸
    windowManager.defaultDisplay.getMetrics(dm)// 给白纸设置宽高
    return dm.heightPixels
}

fun Context.dp2px(dpValue: Number): Int {
    val scale = resources.displayMetrics.density
    return (dpValue.toFloat() * scale + 0.5f).toInt()
}

/**
 * sp转px
 * @param spValue sp值
 * @return px值
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}