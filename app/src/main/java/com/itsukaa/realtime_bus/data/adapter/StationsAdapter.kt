package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Station


@SuppressLint("SetTextI18n")
class StationsAdapter(
    private var stations: MutableList<Station>
) :
    RecyclerView.Adapter<StationsAdapter.StationsViewHolder>() {

    lateinit var context: Context

    /**
     * 初始化
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        context = parent.context

        return StationsViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_station_home_fragment_recycle_view,
                null
            )
        )
    }

    /**
     * 视图数据绑定
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        val station: Station = stations[position]
        val stationLines = station.stationLines

        //如果这条线路 来去 都没有bus，则将该线路删除
//        stationLines!!.removeIf {
//            (it.singleLine!!.singleLineBuses.isNullOrEmpty()
//                    && it.returnSingleLine!!.singleLineBuses.isNullOrEmpty())
//        }

        //渲染界面。
        holder.stopNameTextView.text = "${station.stationName}"
        holder.stopNumTextView.text = "${stationLines!!.size} 条有车线路"
        if (stationLines.size == 0) {
            holder.stopNumTextView.text = "所有线路均无车"
        }

        //子RecycleView
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.recyclerView.layoutManager = linearLayoutManager
        val linesAdapter =
            station.stationLines?.let {
                LinesAdapter(
                    it,
                    station
                )
            }
        holder.recyclerView.adapter = linesAdapter
    }

    override fun getItemCount(): Int {
        return stations.size
    }


    inner class StationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopNameTextView: TextView =
            view.findViewById(R.id.item_bus_home_fragment_recycle_view_stopName)
        val stopNumTextView: TextView =
            view.findViewById(R.id.item_station_home_fragment_recycle_view_stopNum)
        val recyclerView: RecyclerView = view.findViewById(R.id.item_bus_recycleView)
    }

}