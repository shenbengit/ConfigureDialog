package com.shencoder.configuredialogdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.shencoder.configuredialog.ConfigureDialog
import com.shencoder.configuredialog.PasswordDialog

class MainActivity : AppCompatActivity() {
    private companion object {
        private const val TAG = "MainActivity"
    }

    private val passwordDialog by lazy {
        PasswordDialog.builder(this)
            .setCorrectPassword("123456")
            .setOnPasswordCorrectCallback {
                Log.i(TAG, "密码输入正确")
                Toast.makeText(this, "密码输入正确", Toast.LENGTH_SHORT).show()
            }
            .create()
    }
    private val configureDialog by lazy {
        ConfigureDialog.builder(this)
            .setOriginalIp("192.168.2.2")
            .setOriginalPort(12)
            .setOriginalPrisonId("123123")
            .setOnConfigureCallback { ip, port, prisonId ->
                Log.i(TAG, "参数配置，ip：${ip}，port：${port}，prisonId：${prisonId} ")
                Toast.makeText(
                    this,
                    "参数配置，ip：${ip}，port：${port}，prisonId：${prisonId} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnPasswordDialog).setOnClickListener {
            passwordDialog.show()
        }
        findViewById<Button>(R.id.btnConfigureDialog).setOnClickListener {
            configureDialog.show()
        }
    }
}