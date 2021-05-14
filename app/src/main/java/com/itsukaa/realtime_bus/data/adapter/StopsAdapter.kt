package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.SingleLine

@SuppressLint("SetTextI18n")
class StopsAdapter(var singleLine: SingleLine, val stationId: String) :
    RecyclerView.Adapter<StopsAdapter.StopsViewHolder>() {
    lateinit var activity: Activity
    val busesInfo = singleLine.getBusesInfo()!!.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopsViewHolder {
        val viewHolder = StopsViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_stops,
                null
            )
        )
        activity = parent.context as Activity
        return viewHolder
    }

    override fun onBindViewHolder(holder: StopsViewHolder, position: Int) {
        val stations = singleLine.singleLineStations
        val stationName = stations!![position].stationName

        holder.stopName.text = "${stationName}"
        holder.realLine.text = "--->"
        //TODO：标识前后车站

        for (bus in busesInfo) {
            if (bus.busFromStartStationNum == position + 1) {
                holder.busPic.text = "\uD83D\uDE8C"
                if (bus.busIsArrived == true) {
                    holder.busPic.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                } else {
                    holder.busPic.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return singleLine.singleLineStations?.size!!
    }


    inner class StopsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var stopName: TextView
        var realLine: TextView
        var busPic: TextView

        init {
            stopName = view.findViewById(R.id.stopName)
            realLine = view.findViewById(R.id.realLine)
            busPic = view.findViewById(R.id.busPic)
        }
    }
}