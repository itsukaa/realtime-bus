package com.itsukaa.realtime_bus.ui.navi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.AMap
import com.amap.api.maps.TextureMapView
import com.itsukaa.realtime_bus.R
import com.orhanobut.logger.Logger

class NaviFragment : Fragment() {

    lateinit var textureMapView: TextureMapView
    lateinit var aMap: AMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_navi, container, false)

        textureMapView = view!!.findViewById(R.id.aMap)
        textureMapView.onSaveInstanceState(savedInstanceState)
        aMap = textureMapView.map
        Logger.i("地图存在！")
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        textureMapView.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textureMapView.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        textureMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        textureMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        textureMapView.onSaveInstanceState(outState)
    }


}