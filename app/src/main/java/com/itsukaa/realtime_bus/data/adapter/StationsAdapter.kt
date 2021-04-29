package com.itsukaa.realtime_bus.data.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Station


class StationsAdapter(var stations: List<Station>) :
    RecyclerView.Adapter<StationsAdapter.StationsViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        val viewHolder = StationsViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_station_home_fragment_recycle_view,
                null
            )
        )
        context = parent.context

        return viewHolder
    }

    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        val station: Station = stations[position]

        holder.stopNameTextView.text = station.stationName
        holder.stopNumTextView.text = "${station.stationNum}个站台"


        //子RecycleView
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.recyclerView.layoutManager = linearLayoutManager
        holder.recyclerView.adapter =
            station.lines?.let { LinesAdapter(it, R.layout.item_line_home_fragment_recycle_view) }
    }

    override fun getItemCount(): Int {
        return stations.size
    }


    inner class StationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopNameTextView: TextView
        val stopNumTextView: TextView
        val recyclerView: RecyclerView

        init {
            stopNameTextView = view.findViewById(R.id.item_bus_home_fragment_recycle_view_stopName)
            stopNumTextView =
                view.findViewById(R.id.item_station_home_fragment_recycle_view_stopNum)
            recyclerView = view.findViewById(R.id.item_bus_recycleView)
        }
    }
}