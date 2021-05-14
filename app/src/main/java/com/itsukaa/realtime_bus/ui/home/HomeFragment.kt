package com.itsukaa.realtime_bus.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.StationsAdapter
import com.itsukaa.realtime_bus.data.entity.Location
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.server.task.homeTask
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("SetTextI18n")
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    var stations: MutableList<Station> = mutableListOf()
    lateinit var adapter: StationsAdapter

    lateinit var floatingActionButton: FloatingActionButton
    lateinit var refreshLayout: RefreshLayout

    val countDownTimer = object : CountDownTimer(30 * 1000, 1000) {
        override fun onFinish() {
            activity!!.findViewById<TextView>(R.id.daojishi).text = "0"
        }

        override fun onTick(millisUntilFinished: Long) {
            activity!!.findViewById<TextView>(R.id.daojishi).text =
                "${millisUntilFinished / 1000}s"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)

        floatingActionButton = view.findViewById(R.id.fab)
        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setRefreshHeader(ClassicsHeader(context))
        refreshLayout.setRefreshFooter(ClassicsFooter(context))


        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = StationsAdapter(stations)
        adapter = mRecyclerView.adapter as StationsAdapter
        countDownTimer.start()
        refreshData()
        addEventListener()
        addTimerTask()
        return view
    }

    private fun addTimerTask() {
        val timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                refreshData()
            }
        }
        timer.schedule(timerTask, 0, 30000) //立刻执行，间隔1秒循环执行
    }

    private fun addEventListener() {
        floatingActionButton.setOnClickListener {
            refreshData()
        }

        refreshLayout.setOnRefreshListener {
            Logger.i("刷新")
            refreshData()
            it.finishRefresh()
        }

        refreshLayout.setEnableLoadMore(false)

    }

    private fun refreshData() {
        homeTask(activity!!, adapter, stations, Location("114.316107", "30.530555", "武汉"))
        countDownTimer.cancel()
        countDownTimer.start()
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
        val busStations = ArrayList<String>()

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