package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.SingleLine

@SuppressLint("SetTextI18n")
class StopsAdapter(var singleLine: SingleLine, val stationId: String) :
    RecyclerView.Adapter<StopsAdapter.StopsViewHolder>() {


    inner class StopsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var stopName: TextView
        lateinit var realLine: TextView
        lateinit var busPic: TextView

        init {
            stopName = view.findViewById(R.id.stopName)
            realLine = view.findViewById(R.id.realLine)
            busPic = view.findViewById(R.id.busPic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopsViewHolder {
        val viewHolder = StopsViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_stops,
                null
            )
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: StopsViewHolder, position: Int) {
        val stations = singleLine.singleLineStations
        val stationName = stations!![position].stationName

        holder.stopName.text = "${stationName}"
        holder.busPic.text = "车"
        holder.realLine.text = "-->"
    }

    override fun getItemCount(): Int {
        return singleLine.singleLineStations?.size!!
    }
}