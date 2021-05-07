package com.itsukaa.realtime_bus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import com.amap.api.services.busline.BusStationQuery
import com.amap.api.services.busline.BusStationSearch
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.StationsAdapter
import com.itsukaa.realtime_bus.data.entity.Location
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.server.data.getStationsByLocation
import com.itsukaa.realtime_bus.utils.beautifyStations
import com.orhanobut.logger.Logger


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var stations: MutableList<Station>
    lateinit var adapter: StationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initData()
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = StationsAdapter(stations)
        adapter = mRecyclerView.adapter as StationsAdapter
        return view
    }

    private fun initData() {
        stations = emptyList<Station>().toMutableList()
        Thread {
            var stationsByLocation =
                getStationsByLocation(Location("114.316107", "30.530555", "武汉"))
            stationsByLocation = beautifyStations(stationsByLocation as MutableList)
            stations.addAll(stationsByLocation)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
            Logger.i(stations.toString())
        }.start()
    }


    /**
     * 获取公交信息
     */
    fun getgongjiaoinfo() {
        //"150700"
        val query = PoiSearch.Query("", "150700", "武汉")
        query.pageSize = 10
        query.pageNum = 0
        query.cityLimit = true

        val poiSearch = PoiSearch(context, query)
        var busStations = ArrayList<String>()

        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiSearched(result: PoiResult?, rCode: Int) {
                for (poiItem in result!!.pois) {
                    busStations.add(poiItem.title)
                }
                for (busStation in busStations) {
                    val busStationQuery = BusStationQuery(busStation, "武汉")
                    val busStationSearch = BusStationSearch(context, busStationQuery)
                    busStationSearch.setOnBusStationSearchListener { busStationResult, _ ->
                        for (item in busStationResult.busStations) {
                            for (busLineItem in item.busLineItems) {
                                val busLineQuery = BusLineQuery(
                                    busLineItem.busLineId,
                                    BusLineQuery.SearchType.BY_LINE_ID,
                                    "武汉"
                                )
                                busLineQuery.pageSize = 10
                                busLineQuery.pageNumber = 0

                                Logger.w(busLineItem.busLineId)
                                val busLineSearch = BusLineSearch(context, busLineQuery)
                                busLineSearch.setOnBusLineSearchListener { busLineResult, _ ->
                                    for (busLine in busLineResult.busLines) {
                                        Logger.w(busLine.directionsCoordinates.toString())
                                    }
                                }
                                busLineSearch.searchBusLineAsyn()
                            }
                        }
                    }
                    busStationSearch.searchBusStationAsyn()
                }

            }

            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                TODO("Not yet implemented")
            }
        })
        poiSearch.searchPOIAsyn()
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(30.555537, 114.314724), 500)
        Logger.w("发送请求")
    }

}