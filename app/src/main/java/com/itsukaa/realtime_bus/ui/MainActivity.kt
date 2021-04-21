package com.itsukaa.realtime_bus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.ui.fragment.home.HomeFragment
import com.itsukaa.realtime_bus.ui.fragment.more.MoreFragment
import com.itsukaa.realtime_bus.ui.fragment.navi.NaviFragment
import com.itsukaa.realtime_bus.ui.fragment.profile.ProfileFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomBar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)


        bottomBar.onTabSelected = {
            var fragment = Fragment()
            when (it.title) {
                "首页" -> {
                    fragment = HomeFragment()
                }
                "导航" -> {
                    fragment = NaviFragment()
                }
                "更多" -> {
                    fragment = MoreFragment()
                }
                "我的" -> {
                    fragment = ProfileFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_view, fragment)
            transaction.commit()
        }
    }

}