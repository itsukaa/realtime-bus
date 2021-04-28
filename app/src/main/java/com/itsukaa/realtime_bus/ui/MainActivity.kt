package com.itsukaa.realtime_bus.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.ui.home.HomeFragment
import com.itsukaa.realtime_bus.ui.more.MoreFragment
import com.itsukaa.realtime_bus.ui.navi.NaviActivity
import com.itsukaa.realtime_bus.ui.profile.ProfileFragment
import com.orhanobut.logger.Logger
import nl.joery.animatedbottombar.AnimatedBottomBar
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPerms()
        setContentView(R.layout.activity_main)

        val bottomBar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)
        bottomBar.onTabSelected = {
            var fragment = Fragment()
            when (it.title) {
                "首页" -> {
                    fragment = HomeFragment()
                }
                "导航" -> {
                    startActivity(Intent(this, NaviActivity::class.java))
                }
                "更多" -> {
                    fragment = MoreFragment()
                }
                "我的" -> {
                    fragment = ProfileFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.commit()
            transaction.replace(R.id.main_fragment_view, fragment)
        }
        bottomBar.selectTabAt(3)
        bottomBar.selectTabAt(0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Logger.i("申请权限成功")
        Logger.i(perms.toString())
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Logger.i("申请权限失败")
        Logger.i(perms.toString())
    }

    private fun checkPerms() {
        val perms =
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
            )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "该App需要获取您的位置信息以及读取SD卡权限", 1, *perms)
        }
    }
}