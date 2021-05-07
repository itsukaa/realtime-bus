package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Line
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.ui.home.LineDetailsActivity
import com.orhanobut.logger.Logger

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

        //事件处理
        val context = parent.context
        viewHolder.lineLayout.setOnClickListener {
            val position = viewHolder.adapterPosition
            val line = lines[position]
            val intent = Intent(context, LineDetailsActivity::class.java)

            intent.putExtra("singleLine", Gson().toJson(line.singleLine))
            intent.putExtra("stationId", station.stationId)

            Logger.i(line.singleLine!!.singleLineId.toString())
            context.startActivity(intent)
            Logger.i("去的线路被点击了")
        }

        viewHolder.rLineLayout.setOnClickListener {
            val position = viewHolder.adapterPosition
            val line = lines[position]
            val intent = Intent(context, LineDetailsActivity::class.java)

            intent.putExtra("singleLine", Gson().toJson(line.returnSingleLine))
            intent.putExtra("stationId", station.stationId)


            Logger.i(line.returnSingleLine!!.singleLineId.toString())
            context.startActivity(intent)
            Logger.i("回的线路被点击了")
        }


        return viewHolder
    }


    override fun onBindViewHolder(holder: LinesAdapter.LinesViewHolder, position: Int) {
        val line: Line = lines[position]
        val singleLine = line.singleLine
        val returnSingleLine = line.returnSingleLine


        //视图更新
        holder.lineName.text = "${singleLine?.singleLineName}路"


        val stopNum = singleLine?.stopNum(station.stationId.toString())
        val leftTime = singleLine?.leftTime(station.stationId.toString())
        val rStopNum = returnSingleLine?.stopNum(station.stationId.toString())
        val rLeftTime = singleLine?.leftTime(station.stationId.toString())

        if (stopNum == -1) {
            holder.lineStationCountTime.text = "已无车"
        } else {
            holder.lineStationCountTime.text = "${stopNum}站/${leftTime}"
        }
        if (rStopNum == -1) {
            holder.rLineStationCountTime.text = "已无车"
        } else {
            holder.rLineStationCountTime.text = "${rStopNum}站/${rLeftTime}"
        }

        if (stopNum == -1 && rStopNum == -1) {

            holder.nameLayout.visibility = GONE
            holder.lineLayout.visibility = GONE
            holder.rLineLayout.visibility = GONE
        }

        holder.lineDirection.text = "开往-> ${singleLine?.singleLineEndStationName} 方向"
        holder.rLineDirection.text = "开往-> ${returnSingleLine?.singleLineEndStationName} 方向"
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

        val lineLayout: RelativeLayout
        val rLineLayout: RelativeLayout
        val nameLayout: RelativeLayout

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
            lineLayout =
                view.findViewById(R.id.item_line_0_layout)
            rLineLayout =
                view.findViewById(R.id.item_line_1_layout)
            nameLayout =
                view.findViewById(R.id.item_line_name_layout)

        }
    }

}