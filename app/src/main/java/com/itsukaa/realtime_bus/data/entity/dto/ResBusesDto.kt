package com.itsukaa.realtime_bus.data.entity.dto

import com.itsukaa.realtime_bus.data.entity.Bus

class ResBusesDto {
    val code: Int = 200
    val msg: String = "ok"
    val res: List<Bus>? = null
    override fun toString(): String {
        return "ResBusesDto(res=$res)"
    }
}