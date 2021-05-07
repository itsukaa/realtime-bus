package com.itsukaa.realtime_bus.utils

import com.itsukaa.realtime_bus.data.entity.Station

fun beautifyStations(stations: MutableList<Station>): MutableList<Station> {
    for (station in stations) {
        if (station.stationLines == null) {
            stations.remove(station)
            continue
        }
        if (station.stationLines!!.size == 0) {
            stations.remove(station)
            continue
        }
    }

    return stations
}