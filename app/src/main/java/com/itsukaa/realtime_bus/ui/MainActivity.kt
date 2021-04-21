package com.itsukaa.realtime_bus.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.itsukaa.realtime_bus.R
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {
    lateinit var mMapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.i("map已生成！")

        mMapView = findViewById(R.id.map)
        mMapView.onCreate(savedInstanceState)
        val aMap = mMapView.map!!
        aMap.setOnMapClickListener {
            startActivity(Intent(this, LaunchActivity::class.java))
        }

        MyLocationStyle().apply {
            interval(2000)
            myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
            showMyLocation(true)
            anchor(1f, 1f)
            strokeColor(Color.BLUE)
            strokeColor(Color.GREEN)

            aMap.myLocationStyle = this
            aMap.isMyLocationEnabled = true
            aMap.uiSettings.isMyLocationButtonEnabled = true
            aMap.showIndoorMap(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("map已销毁！")
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mMapView.onSaveInstanceState(outState)
    }

}