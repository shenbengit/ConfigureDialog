package com.shencoder.configuredialog

/**
 *
 * @author  ShenBen
 * @date    2021/05/19 13:44
 * @email   714081644@qq.com
 */
interface OnPasswordCorrectCallback {

    /**
     * 密码输入错误回调
     * @return true: 隐藏dialog ; false: 不做处理
     */
    fun onPasswordWrong(): Boolean {
        return false
    }

    /**
     * 密码正确回调
     */
    fun onPasswordCorrect()
}