package com.itsukaa.realtime_bus.server.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itsukaa.realtime_bus.data.entity.*
import com.itsukaa.realtime_bus.data.entity.dto.ResBusesDto
import com.itsukaa.realtime_bus.data.entity.dto.ResStationsDto
import com.itsukaa.realtime_bus.server.net.NetClient
import com.orhanobut.logger.Logger


fun getStationsByLocation(location: Location): List<Station> {
    return try {
        val path = "/stations/nearBy"
        val body = location.toString()
        val res = NetClient.post(path, body).execute().body().string()

        val gson = Gson()
        val fromJson = gson.fromJson(res, ResStationsDto::class.java)
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
    return try {
        val path = "/buses/$lineId"
        val res = NetClient.get(path).execute().body().string()
        val gson = Gson()
        val fromJson = gson.fromJson(res, ResBusesDto::class.java)
        return fromJson.res as List<Bus>
    } catch (exception: Exception) {
        Logger.e("数据出错")
        emptyList()
    }
}


fun getSingleLinesByName(name: String): MutableList<SingleLine> {
    Logger.d(name)
    val path = "/singleLines/$name"
    val res = NetClient.get(path).execute().body().string()
    val gson = Gson()
    return try {
        val fromJson: MutableList<SingleLine> =
            gson.fromJson(res, object : TypeToken<ArrayList<SingleLine>>() {}.type)
        Logger.d(fromJson)
        fromJson
    } catch (exception: Exception) {
        Logger.e("数据出错")
        mutableListOf()
    }
}