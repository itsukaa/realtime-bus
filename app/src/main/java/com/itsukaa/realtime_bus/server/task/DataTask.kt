package com.itsukaa.realtime_bus.server.task

import android.app.Activity
import com.itsukaa.realtime_bus.data.adapter.StationsAdapter
import com.itsukaa.realtime_bus.data.entity.Location
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.server.data.getStationsByLocation
import com.itsukaa.realtime_bus.utils.beautifyStations

fun homeTask(
    activity: Activity,
    adapter: StationsAdapter,
    stations: MutableList<Station>,
    location: Location
) {
    //Location("114.316107", "30.530555", "武汉")
    Thread {
        var stationsByLocation = getStationsByLocation(location)
        if (stationsByLocation.isNotEmpty()) {
            stationsByLocation = beautifyStations(stationsByLocation as MutableList)
            stations.clear()
            stations.addAll(stationsByLocation)
            activity.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }.start()
}