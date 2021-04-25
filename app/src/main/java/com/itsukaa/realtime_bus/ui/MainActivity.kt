package com.itsukaa.realtime_bus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.ui.home.HomeFragment
import com.itsukaa.realtime_bus.ui.home.LaunchActivity
import com.itsukaa.realtime_bus.ui.navi.NaviActivity
import com.itsukaa.realtime_bus.ui.profile.ProfileFragment
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
                    fragment = HomeFragment.newInstance("123", "213")
                }
                "导航" -> {
                    startActivity(Intent(this, LaunchActivity::class.java))
                }
                "更多" -> {
                    startActivity(Intent(this, NaviActivity::class.java))
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