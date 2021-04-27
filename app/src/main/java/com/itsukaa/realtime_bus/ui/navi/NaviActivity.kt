package com.itsukaa.realtime_bus.ui.navi

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.itsukaa.realtime_bus.R
import com.orhanobut.logger.Logger

class NaviActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

        val mapView = findViewById<MapView>(R.id.map)
        mapView.onCreate(savedInstanceState)
        val aMap = mapView.map
        Logger.i("地图已被创建")
        Logger.i(aMap.toString())

        val searchView = findViewById<SearchView>(R.id.navi_search_view)
        searchView.setOnClickListener {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        val mapView = findViewById<MapView>(R.id.map)
        mapView.onDestroy()
        Logger.i("地图已被销毁")
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        val mapView = findViewById<MapView>(R.id.map)
        mapView.onResume()
        Logger.i("地图已被重新创建")
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        val mapView = findViewById<MapView>(R.id.map)
        mapView.onPause()
        Logger.i("地图已被暂停")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val mapView = findViewById<MapView>(R.id.map)
        mapView.onSaveInstanceState(outState)
        Logger.i("地图状态已被保存")
    }


}