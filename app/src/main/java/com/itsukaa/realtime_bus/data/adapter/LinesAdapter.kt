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

@SuppressLint("SetTextI18n")
class LinesAdapter(
    private var lines: MutableList<Line>,
    private val station: Station
) :
    RecyclerView.Adapter<LinesAdapter.LinesViewHolder>() {

    private var hideLines: MutableList<Line> = emptyList<Line>().toMutableList()
    private var isOpen: Boolean = false

    //初始化holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        val holder = LinesViewHolder(
            View.inflate(
                parent.context,
                R.layout.item_line_home_fragment_recycle_view,
                null
            )
        )
        //设置事件监听
        val context = parent.context
        //点击具体线路，进入详情Activity
        holder.lineLayout.setOnClickListener {
            val position = holder.adapterPosition
            val line = lines[position]
            val intent = Intent(context, LineDetailsActivity::class.java)
            intent.putExtra("singleLine", Gson().toJson(line.singleLine))
            intent.putExtra("station", Gson().toJson(station))
            context.startActivity(intent)
        }
        //相反方向
        holder.rLineLayout.setOnClickListener {
            val position = holder.adapterPosition
            val line = lines[position]
            val intent = Intent(context, LineDetailsActivity::class.java)
            intent.putExtra("singleLine", Gson().toJson(line.returnSingleLine))
            intent.putExtra("station", Gson().toJson(station))
            context.startActivity(intent)
        }
        //设置点击
        holder.openTipTextView.setOnClickListener {
            if (!isOpen) {//本来是折叠的
                isOpen = true
                lines.addAll(hideLines)
                hideLines.clear()
                notifyDataSetChanged()
            } else {
                isOpen = false
                setData()
                notifyDataSetChanged()
            }
        }

        return holder
    }

    //视图数据绑定
    override fun onBindViewHolder(holder: LinesAdapter.LinesViewHolder, position: Int) {
        val line: Line = lines[position]
        val singleLine = line.singleLine
        val returnSingleLine = line.returnSingleLine


        //视图更新
        holder.lineName.text = "${singleLine?.singleLineName}路"
        holder.lineDirection.text = "${singleLine?.singleLineEndStationName} 方向"
        holder.rLineDirection.text = "${returnSingleLine?.singleLineEndStationName} 方向"
        //获取来去线路距离该 station 最近的 bus
        val stopNum = singleLine?.stopNum(station.stationId.toString())
        val rStopNum = returnSingleLine?.stopNum(station.stationId.toString())
        if (stopNum == -1) {
            holder.lineStationCountTime.text = "暂无车辆"
        } else {
            val numAndCount = singleLine?.getNumAndCount(station.stationId.toString())
            holder.lineStationCountTime.text = "$numAndCount"
        }
        if (rStopNum == -1) {
            holder.rLineStationCountTime.text = "暂无车辆"
        } else {
            val numAndCount = returnSingleLine?.getNumAndCount(station.stationId.toString())
            holder.rLineStationCountTime.text = "$numAndCount"
        }
        //剔除反方向Err
        if (returnSingleLine == null) {
            holder.rLineLayout.visibility = GONE
        }

        //是否添加 “展开” “收起” 按钮
        if (!isOpen) {
            if (!hideLines.isEmpty() && position == 3) {
                //只有在 hideLine 不为空，而且当前 line 是第四位的时候，才会添加展开按钮
                holder.openTipTextView.visibility = View.VISIBLE
                holder.openTipTextView.text = "展开"
            } else {
                holder.openTipTextView.visibility = View.GONE
            }
        } else {
            if (position == lines.size - 1) {
                //只有在 hideLine 不为空，而且当前 line 是第四位的时候，才会添加展开按钮
                holder.openTipTextView.visibility = View.VISIBLE
                holder.openTipTextView.text = "收起"
            } else {
                holder.openTipTextView.visibility = View.GONE
            }
        }
    }


    override fun getItemCount(): Int {
        setData()
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
        val openTipTextView: TextView

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
            openTipTextView =
                view.findViewById(R.id.open_tip)
        }
    }

    /**
     * 削减 lines
     * 将削减下来的 lines 放进 hideLines
     */
    private fun setData() {
        if (!isOpen) {
            val newLines: MutableList<Line> = emptyList<Line>().toMutableList()
            lines.indices.forEach { i ->
                if (i < 4) {
                    newLines.add(lines[i])
                } else {
                    hideLines.add(lines[i])
                }
            }
            lines = newLines
        }
    }
}