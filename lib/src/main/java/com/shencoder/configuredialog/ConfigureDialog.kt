package com.shencoder.configuredialog

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.annotation.StyleRes

/**
 * 参数配置Dialog
 *
 * @author  ShenBen
 * @date    2021/04/23 10:55
 * @email   714081644@qq.com
 */
class ConfigureDialog private constructor(builder: Builder) :
    Dialog(builder.context, builder.themeResId) {

    companion object {
        /**
         * ip正则
         */
        private const val IP_REGEX =
            "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"

        /**
         * 合法端口号范围
         */
        private val PORT_RANGE = 0..65535

        @JvmStatic
        fun builder(context: Context) = Builder(context)

        @JvmStatic
        fun builder(context: Context, @StyleRes themeResId: Int) = Builder(context, themeResId)
    }

    private val etIp: EditText
    private val etPort: EditText
    private val etPrisonId: EditText


    init {
        setContentView(R.layout.dialog_configure)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        findViewById<TextView>(R.id.tvCancel).setOnClickListener { cancel() }
        etIp = findViewById(R.id.etIp)
        etPort = findViewById(R.id.etPort)
        etPrisonId = findViewById(R.id.etPrisonId)

        findViewById<TextView>(R.id.tvSure).setOnClickListener {
            val ip = etIp.text.toString().trim()
            val portStr = etPort.text.toString().trim()
            val prisonId = etPrisonId.text.toString().trim()
            if (ip.isBlank() || portStr.isBlank() || prisonId.isBlank()) {
                context.toast("配置内容不能为空")
                return@setOnClickListener
            }

            if (ip.matches(Regex(IP_REGEX)).not()) {
                context.toast("IP 地址错误")
                return@setOnClickListener
            }
            val port = portStr.toInt()
            if (port !in PORT_RANGE) {
                context.toast("端口号范围[0,65535]")
                return@setOnClickListener
            }
            cancel()
            builder.onConfigureCallback?.onConfigure(ip, port, prisonId)
        }
        etIp.setText(builder.originalIp)
        val port = if (builder.originalPort == Builder.DEFAULT_PORT) {
            ""
        } else {
            builder.originalPort.toString()
        }
        etPort.setText(port)
        etPrisonId.setText(builder.originalPrisonId)
    }


    class Builder @JvmOverloads internal constructor(
        internal val context: Context,
        @StyleRes internal val themeResId: Int = R.style.ConfigureDialog
    ) {
        companion object {
            const val DEFAULT_PORT = -1
        }

        /**
         * 服务器ip地址
         */
        internal var originalIp: String? = null
        fun setOriginalIp(ip: String?) = apply { originalIp = ip }

        /**
         * 服务器端口号
         */
        internal var originalPort: Int = DEFAULT_PORT
        fun setOriginalPort(@IntRange(from = 0, to = 65535) port: Int) =
            apply { originalPort = port }

        /**
         * 所编号
         */
        internal var originalPrisonId: String? = null
        fun setOriginalPrisonId(prisonId: String?) = apply { originalPrisonId = prisonId }

        /**
         * 参数配置回调
         */
        internal var onConfigureCallback: OnConfigureCallback? = null
        fun setOnConfigureCallback(callback: OnConfigureCallback?) = apply {
            onConfigureCallback = callback
        }

        inline fun setOnConfigureCallback(crossinline callback: (ip: String, port: Int, prisonId: String) -> Unit = { _, _, _ -> }) =
            setOnConfigureCallback(object : OnConfigureCallback {
                override fun onConfigure(ip: String, port: Int, prisonId: String) {
                    callback.invoke(ip, port, prisonId)
                }
            })

        fun create() = ConfigureDialog(this)
    }

}
