package com.shencoder.configuredialog

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes

/**
 *
 * @author  ShenBen
 * @date    2021/04/23 10:31
 * @email   714081644@qq.com
 */

class PasswordDialog private constructor(
    builder: Builder
) : Dialog(builder.context, builder.themeResId) {
    private val etPassword: EditText

    companion object {

        @JvmStatic
        fun builder(context: Context) = Builder(context)

        @JvmStatic
        fun builder(context: Context, @StyleRes themeResId: Int) = Builder(context, themeResId)
    }

    init {
        setContentView(R.layout.dialog_password)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        findViewById<TextView>(R.id.tvCancel).setOnClickListener { cancel() }
        etPassword = findViewById(R.id.etPassword)
        findViewById<TextView>(R.id.tvSure).setOnClickListener {
            val str = etPassword.text.toString().trim()
            if (str.isBlank()) {
                context.toast("请输入密码")
                return@setOnClickListener
            }
            if (str != builder.mCorrectPassword) {
                context.toast("密码错误")
                return@setOnClickListener
            }
            cancel()
            builder.onPasswordCorrectCallback?.onPasswordCorrect()
        }
    }

    override fun show() {
        etPassword.text = null
        super.show()
    }

    class Builder @JvmOverloads internal constructor(
        internal val context: Context,
        @StyleRes internal val themeResId: Int = R.style.ConfigureDialog
    ) {
        /**
         * 设置正确的密码
         */
        internal var mCorrectPassword: String = ""
        fun setCorrectPassword(password: String) = apply { mCorrectPassword = password }

        /**
         * 密码输入正确后回调
         */
        internal var onPasswordCorrectCallback: OnPasswordCorrectCallback? = null
        fun setOnPasswordCorrectCallback(callback: OnPasswordCorrectCallback?) = apply {
            onPasswordCorrectCallback = callback
        }

        inline fun setOnPasswordCorrectCallback(crossinline callback: () -> Unit = {}) =
            setOnPasswordCorrectCallback(object : OnPasswordCorrectCallback {
                override fun onPasswordCorrect() {
                    callback.invoke()
                }
            })

        fun create() = PasswordDialog(this)
    }
}