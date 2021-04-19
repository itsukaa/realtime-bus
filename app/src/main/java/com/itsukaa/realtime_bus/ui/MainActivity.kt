package com.itsukaa.realtime_bus.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.itsukaa.realtime_bus.R

class MainActivity : AppCompatActivity() {
    lateinit var mMapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMapView = findViewById(R.id.map)
        mMapView.onCreate(savedInstanceState)
        val aMap = mMapView.map!!

        MyLocationStyle().apply {
            interval(2000)
            myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
            showMyLocation(true)
            anchor(1f, 1f)
            strokeColor(Color.BLUE)
            strokeColor(Color.GREEN)

            aMap.myLocationStyle = this
            aMap.isMyLocationEnabled = true
            aMap.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()

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