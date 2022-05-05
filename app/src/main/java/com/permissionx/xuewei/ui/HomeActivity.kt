package com.permissionx.xuewei.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.permissionx.xuewei.R

class HomeActivity : AppCompatActivity() {
    val fragmentList = listOf(FirstFragment(),SecondFragment(),ThirdFragment())
    private lateinit var contentViewPager: ViewPager
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognition)
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        contentViewPager = findViewById(R.id.content_view_pager)
        //设置 fragment页面的缓存数量 , 这里设置成缓存所有的页面！！！！！
        contentViewPager.offscreenPageLimit = fragmentList.size
        contentViewPager.adapter = Adapter(supportFragmentManager)
        bottomNav = findViewById(R.id.bottom_nav)
        actionBar?.hide()
        initListener()
    }

    override fun onStart() {
        super.onStart()
    }

    inner class Adapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    private fun initListener(){
        // 给底部导航栏的菜单项添加点击事件
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                // smoothScroll=false这个参数能解决切换时的多页闪烁问题
                R.id.first_b -> contentViewPager.setCurrentItem(0, false)
                R.id.identify_b -> contentViewPager.setCurrentItem(1, false)
                R.id.mine_b -> contentViewPager.setCurrentItem(2, false)
            }
            false
        }
        // 当页面切换时，将对应的底部导航栏菜单项设置为选中状态
        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p: Int, pOffset: Float, pOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                // 将对应的底部导航栏菜单项设置为选中状态
                bottomNav.menu.getItem(position).isChecked = true
            }
        })
    }
}