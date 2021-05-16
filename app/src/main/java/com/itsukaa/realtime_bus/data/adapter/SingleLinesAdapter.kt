package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.amap.api.services.busline.BusStationQuery
import com.amap.api.services.busline.BusStationSearch
import com.google.gson.Gson
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.SingleLine
import com.itsukaa.realtime_bus.ui.home.LineDetailsActivity
import com.orhanobut.logger.Logger

@SuppressLint("SetTextI18n")
class SingleLinesAdapter(
    var singleLines: MutableList<SingleLine>
) :
    RecyclerView.Adapter<SingleLinesAdapter.SingleLinesViewHolder>(), AMapLocationListener {

    var nowLatlng: LatLng = LatLng(30.530555, 114.316107)
    lateinit var myContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleLinesViewHolder {
        val viewHolder = SingleLinesViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_houxuan_single_line,
                null
            )
        )
        myContext = parent.context
        return viewHolder
    }

    override fun onBindViewHolder(holder: SingleLinesViewHolder, position: Int) {
        holder.singLineTextView.text =
            "${singleLines[position].singleLineName}路 开往 ${singleLines[position].singleLineEndStationName} 方向"

        val singleLineStations = singleLines[position].singleLineStations!!
        val closestStation = singleLineStations[singleLineStations.size - 1]
        var dis = 10000000f
        for (station in singleLineStations) {
            val stationName = station.stationName
            val busStationQuery = BusStationQuery(stationName, "027")
            val busStationSearch = BusStationSearch(myContext, busStationQuery)
            busStationSearch.setOnBusStationSearchListener { result, i ->
                val busStation = result.busStations[0]
                val calculateLineDistance = AMapUtils.calculateLineDistance(
                    LatLng(
                        busStation.latLonPoint.latitude,
                        busStation.latLonPoint.longitude
                    ), nowLatlng
                )
                if (calculateLineDistance < dis) {
                    dis = calculateLineDistance
                    closestStation.stationName = busStation.busStationName
                }
            }
            busStationSearch.searchBusStationAsyn()
        }


        holder.singLineTextView.setOnClickListener {
            Logger.d("被点击了")
            val intent = Intent(myContext, LineDetailsActivity::class.java)
            Logger.d(Gson().toJson(singleLines[position]))
            intent.putExtra("singleLine", Gson().toJson(singleLines[position]))
            intent.putExtra("station", Gson().toJson(closestStation))
            myContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return singleLines.size
    }

    inner class SingleLinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val singLineTextView = view.findViewById<TextView>(R.id.singleLine)
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        nowLatlng = LatLng(aMapLocation!!.latitude, aMapLocation.longitude)
    }
}