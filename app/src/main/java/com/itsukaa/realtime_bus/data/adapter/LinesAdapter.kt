package com.itsukaa.realtime_bus.data.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Line

class LinesAdapter(var lines: List<Line>, val layoutId: Int) :
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

        holder.lineNameTextView.text = "${line.lineName}路"
        holder.lineDirectionTextView.text = "开往 ${line.endStopName} 方向"
        holder.lineTimeTextView.text = "3分钟"
        holder.lineStationCountTextView.text = "3站/300m"

    }

    override fun getItemCount(): Int {
        return lines.size
    }


    inner class LinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lineNameTextView: TextView
        val lineDirectionTextView: TextView
        val lineTimeTextView: TextView
        val lineStationCountTextView: TextView


        init {
            lineNameTextView =
                view.findViewById(R.id.item_station_home_fragment_recycle_view_basicInfo_stationName_textView)
            lineDirectionTextView =
                view.findViewById(R.id.item_line_home_fragment_recycle_view_basicInfo_stationDirection_textView)
            lineTimeTextView =
                view.findViewById(R.id.item_line_home_fragment_recycle_view_basicInfo_stationTime_textView)
            lineStationCountTextView =
                view.findViewById(R.id.item_line_home_fragment_recycle_view_basicInfo_stationCount_textView)
        }
    }

}