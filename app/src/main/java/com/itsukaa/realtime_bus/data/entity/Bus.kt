package com.itsukaa.realtime_bus.data.entity

import android.location.Location

class Bus {
    /**
     * 公交车唯一标识
     */
    val busId: String? = null

    /**
     * 公交车将要到达或以及到达的站台 距离 起点站台的站台数
     */
    val busFromStartStationNum: Int? = null

    /**
     * 公交车是否已经到达上述站台
     */
    val busIsArrived: Boolean? = null

    /**
     * 公交车的位置
     */
    val location: Location? = null

    /**
     * 什么？
     */
    val busFlag: String? = null
}