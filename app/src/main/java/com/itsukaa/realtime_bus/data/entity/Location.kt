package com.itsukaa.realtime_bus.data.entity

class Location(
    var longitude: String,
    var latitude: String,
    val cityName: String

) {
    override fun toString(): String {
        return "{\"longitude\":\"$longitude\", \"latitude\":\"$latitude\", \"cityName\":\"$cityName\"}"
    }
}