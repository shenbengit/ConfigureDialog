package com.shencoder.configuredialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
    private val builder: Builder
) : Dialog(builder.context, builder.themeResId) {

    companion object {

        @JvmStatic
        fun builder(context: Context, correctPassword: GetCorrectPassword) =
            Builder(context, correctPassword)

        @JvmStatic
        fun builder(context: Context, correctPassword: () -> String) =
            builder(context, object : GetCorrectPassword {
                override val correctPassword: String
                    get() = correctPassword()
            })
    }

    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val onPasswordCorrectCallback = builder.onPasswordCorrectCallback

            if (TextUtils.equals(str, builder.correctPassword.correctPassword).not()) {
                if (onPasswordCorrectCallback != null && onPasswordCorrectCallback.onPasswordWrong()) {
                    cancel()
                } else {
                    context.toast("密码错误！")
                }
                return@setOnClickListener
            }
            cancel()
            onPasswordCorrectCallback?.onPasswordCorrect()
        }
    }

    override fun onStop() {
        super.onStop()
        etPassword.text = null
    }

    class Builder internal constructor(
        internal val context: Context,
        internal val correctPassword: GetCorrectPassword
    ) {
        @StyleRes
        internal var themeResId: Int = R.style.ConfigureDialog
        fun setThemeResId(@StyleRes themeResId: Int) = apply { this.themeResId = themeResId }

        /**
         * 密码输入正确后回调
         */
        internal var onPasswordCorrectCallback: OnPasswordCorrectCallback? = null
        fun setOnPasswordCorrectCallback(callback: OnPasswordCorrectCallback?) = apply {
            onPasswordCorrectCallback = callback
        }

        inline fun setOnPasswordCorrectCallback(
            crossinline correct: () -> Unit = {},
            crossinline wrong: () -> Boolean = { false }
        ) =
            setOnPasswordCorrectCallback(object : OnPasswordCorrectCallback {

                override fun onPasswordWrong(): Boolean {
                    return wrong()
                }

                override fun onPasswordCorrect() {
                    correct()
                }
            })

        fun create() = PasswordDialog(this)
    }
}