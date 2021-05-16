package com.itsukaa.realtime_bus.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.entity.SingleLine
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.ui.home.LineDetailsActivity

@SuppressLint("SetTextI18n")
class SingleLinesAdapter(
    var singleLines: MutableList<SingleLine>,
    val closestStation: Station
) :
    RecyclerView.Adapter<SingleLinesAdapter.SingleLinesViewHolder>() {

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
        holder.selectedTextView.text =
            "${singleLines[position].singleLineName}路 开往 ${singleLines[position].singleLineEndStationName} 方向"

        holder.selectedTextView.setOnClickListener {
            val intent = Intent(myContext, LineDetailsActivity::class.java)
            intent.putExtra("singleLine", Gson().toJson(singleLines[position]))
            intent.putExtra("station", Gson().toJson(closestStation))
            myContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return singleLines.size
    }


    inner class SingleLinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val selectedTextView = view.findViewById<TextView>(R.id.singleLine)
    }
}