package com.itsukaa.realtime_bus.data.entity.dto

import com.itsukaa.realtime_bus.data.entity.Station

class ResStationsDto {
    val code: Int = 200
    val msg: String = "ok"
    val res: List<Station>? = null
    override fun toString(): String {
        return "ResStationsDto(msg='$msg')"
    }


}