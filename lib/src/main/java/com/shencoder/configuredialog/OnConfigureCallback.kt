package com.shencoder.configuredialog

/**
 *
 * @author  ShenBen
 * @date    2021/05/19 13:34
 * @email   714081644@qq.com
 */
interface OnConfigureCallback {
    /**
     * 参数配置回调
     * @param ip        服务器ip
     * @param port      服务器端口
     * @param prisonId  所编号
     */
    fun onConfigure(ip: String, port: Int, prisonId: String)
}