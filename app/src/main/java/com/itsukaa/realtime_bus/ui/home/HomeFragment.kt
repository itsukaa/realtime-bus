package com.itsukaa.realtime_bus.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
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


@SuppressLint("SetTextI18n")
class HomeFragment : Fragment(), AMapLocationListener {

    var stations: MutableList<Station> = mutableListOf()
    lateinit var adapter: StationsAdapter
    private var nowLocation: Location = Location("114.316107", "30.530555", "武汉")
    var flag = false

    lateinit var myActivity: Activity
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var refreshLayout: RefreshLayout
    lateinit var daojishiView: TextView
    lateinit var aMapLocationClient: AMapLocationClient

    val countDownTimer = object : CountDownTimer(30 * 1000, 1000) {
        override fun onFinish() {
            daojishiView.text = "0"
        }

        override fun onTick(millisUntilFinished: Long) {
            daojishiView.text =
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

        daojishiView = view.findViewById(R.id.daojishi)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = StationsAdapter(stations)
        adapter = mRecyclerView.adapter as StationsAdapter
        countDownTimer.start()
        myActivity = activity!!
        refreshData()
        addEventListener()
        addTimerTask()
        val aMapLocationClientOption = AMapLocationClientOption()
        aMapLocationClientOption.interval = 1000
        aMapLocationClient = AMapLocationClient(context!!.applicationContext)
        aMapLocationClient.setLocationListener(this)
        aMapLocationClient.setLocationOption(aMapLocationClientOption)
        aMapLocationClient.startLocation()

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
        if (nowLocation.latitude != "30.530555" && nowLocation.longitude != "114.316107") {
            homeTask(myActivity, adapter, stations, nowLocation)
            Logger.d(nowLocation)
            countDownTimer.cancel()
            countDownTimer.start()
        }
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        nowLocation =
            Location(aMapLocation!!.longitude.toString(), aMapLocation.latitude.toString(), "武汉")
        if (!flag) {
            refreshData()
            flag = true
        }
    }
}