package com.example.huanxinchat

import android.util.Log
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError

/**
 * create by apple
 * create on 2020/10/9
 * description
 */
open class ConnectionListener : EMConnectionListener {

    override fun onConnected() {
        Log.i("minfo", "账号连接成功")
    }

    override fun onDisconnected(errorCode: Int) {
        when(errorCode) {
            EMError.USER_REMOVED ->
                Log.i("minfo", "用户已被删除")
            EMError.USER_LOGIN_ANOTHER_DEVICE ->
                Log.i("minfo", "异地登录被挤掉")

        }
    }

}