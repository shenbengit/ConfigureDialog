package com.shencoder.configuredialog

import android.content.Context
import android.widget.Toast

/**
 *
 * @author  ShenBen
 * @date    2021/05/19 11:52
 * @email   714081644@qq.com
 */
internal fun Context.toast(text: String) {
    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
}