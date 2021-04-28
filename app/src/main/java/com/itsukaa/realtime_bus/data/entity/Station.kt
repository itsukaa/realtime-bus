package com.itsukaa.realtime_bus.data.entity

class Station(var stationName: String, var stationNum: Int, var lines: List<Line>?) {
    init {
        print(stationName)
        print(stationNum)
        print(lines)
    }
}