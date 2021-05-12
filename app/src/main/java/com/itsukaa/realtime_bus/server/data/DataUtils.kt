package com.itsukaa.realtime_bus.server.data

import com.google.gson.Gson
import com.itsukaa.realtime_bus.data.entity.Bus
import com.itsukaa.realtime_bus.data.entity.Line
import com.itsukaa.realtime_bus.data.entity.Location
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.data.entity.dto.ResStationsDto
import com.itsukaa.realtime_bus.server.net.NetClient
import com.orhanobut.logger.Logger

fun getStationsByLocation(location: Location): List<Station> {
    return try {
        val path = "/stations/nearBy"
        val body = location.toString()
        val res = NetClient.call(path, body).execute().body().string()

        val gson = Gson()
        val fromJson = gson.fromJson(res, ResStationsDto::class.java)

        Logger.w("得到数据为${fromJson}")
        fromJson.res as List<Station>
    } catch (exception: Exception) {
        Logger.e("数据出错")
        emptyList()
    }
}


fun getLinesByStationId(stationId: String): List<Line> {
    return emptyList()
}


fun getBusesByLineId(lineId: String): List<Bus> {
    return emptyList()
}