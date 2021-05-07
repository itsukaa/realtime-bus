package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Line
import com.itsukaa.realtime_bus.data.entity.Station

@SuppressLint("SetTextI18n")
class LinesAdapter(var lines: List<Line>, val layoutId: Int, val station: Station) :
    RecyclerView.Adapter<LinesAdapter.LinesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        val viewHolder = LinesViewHolder(
            View.inflate(
                parent.context,
                layoutId,
                null
            )
        )

        return viewHolder
    }


    override fun onBindViewHolder(holder: LinesAdapter.LinesViewHolder, position: Int) {
        val line: Line = lines[position]
        val singleLine = line.singleLine
        val returnSingleLine = line.returnSingleLine

        holder.lineName.text = "${singleLine?.singleLineName}路"

        holder.lineDirection.text = "开往-> ${singleLine?.singleLineDirection}"
        holder.lineStationCountTime.text =
            "${singleLine?.stopNum(station.stationId.toString())}站/" +
                    "${singleLine?.leftTime(station.stationId.toString())}"

    }

    override fun getItemCount(): Int {
        return lines.size
    }


    inner class LinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lineName: TextView

        val lineDirection: TextView
        val lineStationCountTime: TextView

        val rLineDirection: TextView
        val rLineStationCountTime: TextView


        init {
            lineName =
                view.findViewById(R.id.item_line_name)
            lineDirection =
                view.findViewById(R.id.item_line_direction)
            lineStationCountTime =
                view.findViewById(R.id.item_line_stationNum_time)
            rLineDirection =
                view.findViewById(R.id.item_line_reversed_direction)
            rLineStationCountTime =
                view.findViewById(R.id.item_line_reversed_stationNum_time)
        }
    }

}