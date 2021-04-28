package com.itsukaa.realtime_bus.data.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.Station


class StationsAdapter(var stations: List<Station>) :
    RecyclerView.Adapter<StationsAdapter.StationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        val viewHolder = StationsViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_bus_home_fragment_recycle_view,
                null
            )
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        val station: Station = stations[position]

        holder.stopNameTextView.text = station.stationName
        holder.stopNumTextView.text = "${station.stationNum}个站台"
    }

    override fun getItemCount(): Int {
        return stations.size
    }


    inner class StationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopNameTextView: TextView
        val stopNumTextView: TextView

        init {
            stopNameTextView = view.findViewById(R.id.item_bus_home_fragment_recycle_view_stopName)
            stopNumTextView = view.findViewById(R.id.item_bus_home_fragment_recycle_view_stopNum)
        }
    }
}