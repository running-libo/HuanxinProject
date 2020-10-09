package com.example.huanxinchat

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

class MainActivity : BasePermissionActivity() {
    val userName = "YXA6TYDA_OZEQniTrMuCK_z8gQ"
    val psd = "YXA6AuYDeAKIx4hwsIOjVv41MdXen5E"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
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
                imRegisterAndLogin()
            }

            override fun onDenied(deniedPermissions: MutableList<String>?) {

            }

        })
    }

    fun imRegisterAndLogin() {
        //注意：这里的注册有个坑，必须要在子线程中才可以，否则会抛出异常
        Thread {
            kotlin.run {
                //注册账号,注册失败会抛出HyphenateException: Registration failed.
                EMClient.getInstance().createAccount(userName, psd)  //同步方法
                runOnUiThread {
                    //账号登录
                    login()
                }
            }
        }.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun login() {
        EMClient.getInstance().login(userName, psd, object: EMCallBack {
            override fun onSuccess() {
                Log.i("minfo", "登录聊天服务器成功")
            }

            override fun onProgress(progress: Int, status: String?) {

            }

            override fun onError(code: Int, error: String?) {
                Log.i("minfo", "登录聊天服务器失败")
            }

        })
    }

}