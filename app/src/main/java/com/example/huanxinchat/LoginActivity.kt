package com.example.huanxinchat

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : BasePermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestPermissions()

        btnRegister.setOnClickListener {
            gotoRegister()
        }

        btnLogin.setOnClickListener {
            login()
        }

        btnLogout.setOnClickListener {
            logout()
        }

        //添加连接监听器
        EMClient.getInstance().addConnectionListener(ConnectionListener())

    }

    /**
     * App权限申请
     */
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )

        requestPermissions(permissions, object : PermissionListener {
            override fun onGranted() {

            }

            override fun onDenied(deniedPermissions: MutableList<String>?) {

            }

        })
    }

    fun gotoRegister() {
        var userId = etUserId.text.toString()
        var userPsd = etUserPsd.text.toString()
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPsd)) {
            Toast.makeText(this, "请输入完整的账号信息", Toast.LENGTH_SHORT).show()
            return
        }

        imRegisterAndLogin(userId, userPsd)
    }

    private fun imRegisterAndLogin(userId: String, psd: String) {

        //注意：这里的注册有个坑，必须要在子线程中才可以，否则会抛出异常
        Thread {
            kotlin.run {
                //注册账号,注册失败会抛出HyphenateException: Registration failed.
                //比如重复注册，比如在子线程中注册
                try {
                    EMClient.getInstance().createAccount(userId, psd)  //同步方法
                } catch (e: Exception) {
                    Log.i("minfo", "账号已存在")
                }

                runOnUiThread {
                    //账号登录
                    login()
                }
            }
        }.start()
    }

    fun logout() {
        EMClient.getInstance().logout(true, object: EMCallBack {
            override fun onSuccess() {
                Log.i("minfo", "退出登录成功")
            }

            override fun onProgress(progress: Int, status: String?) {

            }

            override fun onError(code: Int, error: String?) {
                Log.i("minfo", "退出登录失败")
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun login() {
        var userId = etUserId.text.toString()
        var userPsd = etUserPsd.text.toString()
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPsd)) {
            Toast.makeText(this, "请输入完整的账号信息", Toast.LENGTH_SHORT).show()
            return
        }

        EMClient.getInstance().login(userId, userPsd, object: EMCallBack {
            override fun onSuccess() {
                Log.i("minfo", "登录聊天服务器成功")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

            override fun onProgress(progress: Int, status: String?) {

            }

            override fun onError(code: Int, error: String?) {
                Log.i("minfo", "登录聊天服务器失败")
            }

        })
    }

}