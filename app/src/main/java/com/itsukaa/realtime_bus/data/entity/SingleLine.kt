package com.itsukaa.realtime_bus.data.entity

class SingleLine {
    val singleLineId: String? = null
    val singleLineName: String? = null
    val singleLineNo: String? = null
    val singleLineDirection: String? = null
    val singleLineStartStationName: String? = null
    val singleLineEndStationName: String? = null
    val singleLineStartTime: String? = null
    val singleLineEndTime: String? = null
    val singleLinePrice: String? = null
    val returnSingleLineId: String? = null
    val singleLineBuses: List<Bus>? = null
    val singleLineStations: List<Station>? = null

    fun stopNum(stationId: String): Int {
        if (singleLineBuses!!.isEmpty()) return -1

        var stationIndex = 0
        for (i in singleLineStations!!.indices) {
            if (stationId == singleLineStations[i].stationId) {
                stationIndex = i + 1
            }
        }

        var stopNum = 0
        for (bus in singleLineBuses) {
            if (bus.busFromStartStationNum!! > stationIndex) break
            stopNum = bus.busFromStartStationNum
        }

        return stationIndex - stopNum
    }


    fun leftTime(stationId: String): String {
        val stopNum = stopNum(stationId)

        if (stopNum == 0) return "已到达"

        return (stopNum * 3 - Math.random() * 2).toString()
    }
}
