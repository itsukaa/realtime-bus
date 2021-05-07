package com.itsukaa.realtime_bus.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.MapView
import com.google.gson.Gson
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.StopsAdapter
import com.itsukaa.realtime_bus.data.entity.SingleLine
import com.orhanobut.logger.Logger

class LineDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_details)

        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onCreate(savedInstanceState)
        val aMap = mapView.map
        Logger.i("地图已被创建")

        val singleLine =
            Gson().fromJson(intent.getStringExtra("singleLine"), SingleLine::class.java)
        val stationId = intent.getStringExtra("stationId")

        Logger.i(singleLine.toString())
        Logger.i(stationId.toString())


        val mRecyclerView = findViewById<RecyclerView>(R.id.stopsView)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = StopsAdapter(singleLine, stationId.toString())


//        val searchView = findViewById<SearchView>(R.id.navi_search_view)
//        searchView.setOnClickListener {
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onDestroy()
        Logger.i("地图已被销毁")
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onResume()
        Logger.i("地图已被重新创建")
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onPause()
        Logger.i("地图已被暂停")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onSaveInstanceState(outState)
        Logger.i("地图状态已被保存")
    }

}