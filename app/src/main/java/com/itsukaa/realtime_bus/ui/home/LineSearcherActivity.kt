package com.itsukaa.realtime_bus.ui.home

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.SingleLinesAdapter
import com.itsukaa.realtime_bus.data.entity.SingleLine
import com.itsukaa.realtime_bus.server.data.getSingleLinesByName
import com.orhanobut.logger.Logger


class LineSearcherActivity : AppCompatActivity() {

    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.ASSERT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bus)

        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var singleLines = mutableListOf<SingleLine>()
        recyclerView.adapter = SingleLinesAdapter(singleLines)

        // 设置搜索文本监听
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                Logger.d(query)
                return false
            }

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                Logger.d(newText)
                Thread {
                    singleLines = getSingleLinesByName(newText)
                    runOnUiThread {
                        recyclerView.adapter = SingleLinesAdapter(
                            singleLines
                        )
                    }
                }.start()
                return false
            }
        })
    }
}