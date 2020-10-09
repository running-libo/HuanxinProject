package com.example.huanxinchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

class MainActivity : AppCompatActivity() {
    val userName = "YXA6TYDA_OZEQniTrMuCK_z8gQ"
    val psd = "YXA6AuYDeAKIx4hwsIOjVv41MdXen5E"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //注册账号,注册失败会抛出HyphenateException: Registration failed.
        EMClient.getInstance().createAccount(userName, psd)  //同步方法

        //账号登录
        login()
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