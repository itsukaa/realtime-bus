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
import com.itsukaa.realtime_bus.data.entity.Line
import com.itsukaa.realtime_bus.data.entity.Station
import com.orhanobut.logger.Logger


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var stations: List<Station>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initData()
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = StationsAdapter(stations)
        return view
    }


    fun initData() {


        val line804 = Line("804", "南湖路江南村")
        val line805 = Line("805", "和平大道杨园")
        val line806 = Line("806", "彭刘杨路")
        val line901 = Line("901", "光谷一路")
        val line902 = Line("902", "余家头")
        val line903 = Line("903", "铁机路")
        val line996 = Line("996", "香港路")

        val lines1 = listOf<Line>(line804, line805, line806, line996)
        val lines2 = listOf<Line>(line901, line902, line903)
        val lines3 = listOf<Line>(line805, line902, line903, line996)


        val station1 = Station("文秀街", 3, lines1)
        val station2 = Station("雄楚大道BRT书城路站", 3, lines2)
        val station3 = Station("文治街", 2, lines3)
        val station4 = Station("雄楚大道BRT出版城", 2, lines3)
        val station5 = Station("香港路", 4, lines1)

        stations = listOf(station1, station2, station3, station5, station4)

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