package com.example.huanxinchat

import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
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
        //需要设置联系人列表才能启动fragment
//        contactListFragment.setContactsMap()
        fragments.add(contactListFragment)
        fragments.add(FindFragment())
        fragments.add(MeFragment())

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

}