package com.itsukaa.realtime_bus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.StationsAdapter
import com.itsukaa.realtime_bus.data.entity.Line
import com.itsukaa.realtime_bus.data.entity.Station

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var stations: List<Station>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initData()
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = StationsAdapter(stations)
        return view
    }


    fun initData() {

        val line804 = Line("804", "南湖路江南村")
        val line805 = Line("805", "和平大道杨园")
        val line806 = Line("806", "彭刘杨路")
        val line901 = Line("901", "光谷一路")
        val line902 = Line("902", "余家头")
        val line903 = Line("903", "铁机路")

        val lines1 = listOf<Line>(line804, line805, line806)
        val lines2 = listOf<Line>(line901, line902, line903)


        val station1 = Station("文秀街", 3, lines1)
        val station2 = Station("雄楚大道BRT书城路站", 3, lines2)

        stations = listOf(station1, station2)


    }


}