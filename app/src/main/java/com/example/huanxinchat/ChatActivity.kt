package com.example.huanxinchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.ui.EaseChatFragment

class ChatActivity : AppCompatActivity() {
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        userId = intent.extras?.getString(EaseConstant.EXTRA_USER_ID).toString()

        val chatFragment = EaseChatFragment()
        val args = Bundle()
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
        args.putString(EaseConstant.EXTRA_USER_ID, userId)
        chatFragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.flChat, chatFragment).commit()
    }

}