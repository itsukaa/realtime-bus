package com.itsukaa.realtime_bus.data.entity

class Station {
    /**
     * 站台位置
     */
    val stationLocation: Location? = null

    /**
     * 站台唯一标识
     */
    val stationId: String? = null

    /**
     * 站台名称
     */
    val stationName: String? = null

    /**
     * 站台线路
     */
    val stationLines: List<Line>? = null
}
