package com.itsukaa.realtime_bus.ui.navi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.itsukaa.realtime_bus.R

class NaviActivity : AppCompatActivity() {
    lateinit var mMapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

        mMapView = findViewById(R.id.map)
        val map = mMapView.map
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