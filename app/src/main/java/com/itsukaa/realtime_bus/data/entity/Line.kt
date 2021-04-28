package com.itsukaa.realtime_bus.data.entity

class Line() {
    constructor(lineName: String, endStopName: String) : this() {
        this.lineName = lineName
        this.endStopName = endStopName
    }

    var lineName: String = ""
    var lineId: Int = 0
    var lineNo: Int = 0
    var direction: Int = 0
    var startStopName: String = ""
    var endStopName: String = ""
    var firstTime: String = ""
    var lastTime: String = ""
    var intervalTime: String = ""
    var price: String = ""
    var lineTimes: String = ""
    var stopsNum: Int = 0
    var measure: Float = 0f
    var beBus: String = ""
    var line2Id: String = ""
    var stops: List<String>? = null
    var buses: List<Bus>? = null


}