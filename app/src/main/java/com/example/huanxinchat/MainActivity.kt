package com.example.huanxinchat

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.domain.EaseUser
import com.hyphenate.easeui.ui.EaseContactListFragment
import com.hyphenate.easeui.ui.EaseConversationListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var fragments = ArrayList<Fragment>()
    val titles = arrayOf("微信", "通讯录", "发现", "我")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments()
        initRadioGroup()
        (rgMenu.getChildAt(0) as RadioButton).isChecked = true
    }

    private fun initRadioGroup() {
        rgMenu.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until rgMenu.childCount) {
                if (rgMenu.getChildAt(i).id == checkedId) {
                    viewPager.currentItem = i
                }

            }
        }
    }

    private fun initFragments() {
        fragments.add(EaseConversationListFragment())
        var contactListFragment = EaseContactListFragment()
        contactListFragment.setContactsMap(createContactList())
        fragments.add(contactListFragment)
        fragments.add(FindFragment())
        fragments.add(MeFragment())

        contactListFragment.setContactListItemClickListener {
            var intent = Intent(this@MainActivity, ChatActivity::class.java)
            intent.putExtra(EaseConstant.EXTRA_USER_ID, it.username)
            startActivity(intent)
        }

        setViewPager()

    }

    private fun setViewPager() {
        viewPager.adapter = FmPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                (rgMenu.getChildAt(position) as RadioButton).isChecked = true
            }

        })
    }

    /**
     * 创建默认联系人列表数据
     */
    private fun createContactList(): HashMap<String, EaseUser> {
        var map = HashMap<String, EaseUser>()
        map["January"] = EaseUser("January")
        map["蘑菇"] = EaseUser("蘑菇")
        map["Miss right"] = EaseUser("Miss right")
        map["神马"] = EaseUser("神马")
        map["豌豆荚"] = EaseUser("豌豆荚")
        map["外星人"] = EaseUser("外星人")
        return map
    }

}