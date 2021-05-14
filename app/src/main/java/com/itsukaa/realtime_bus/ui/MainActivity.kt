package com.itsukaa.realtime_bus.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.ui.home.HomeFragment
import com.orhanobut.logger.Logger
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPerms()
        setContentView(R.layout.activity_main)

        var fragment = HomeFragment()
        val transaction1 = supportFragmentManager.beginTransaction()
        transaction1.commit()
        transaction1.replace(R.id.main_fragment_view, fragment)
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