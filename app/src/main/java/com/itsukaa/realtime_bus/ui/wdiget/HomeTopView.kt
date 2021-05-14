package com.itsukaa.realtime_bus.ui.wdiget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.ui.home.CityPickerActivity
import com.itsukaa.realtime_bus.ui.home.LineSearcherActivity
import com.itsukaa.realtime_bus.ui.navi.NaviActivity
import com.orhanobut.logger.Logger

/**
 *
 */
class HomeTopView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    View.OnClickListener {

    var cityTextView: TextView
    var moreTextView: TextView
    var toNaviTextView: TextView
    var searchView: SearchView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_home_top, this)
        cityTextView = findViewById(R.id.home_top_view_textView_city)
        moreTextView = findViewById(R.id.daojishi)
        toNaviTextView = findViewById(R.id.toNavi)
        searchView = findViewById(R.id.home_top_view_searchView)

        cityTextView.setOnClickListener(this)
        toNaviTextView.setOnClickListener(this)
        searchView.setOnClickListener(this)
        moreTextView.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v == null) {
            Logger.w("点不到东西")
            return
        }
        when (v.id) {
            R.id.home_top_view_searchView -> {
                Logger.w("搜索框被点击了！")
                v.context.startActivity(Intent(v.context, LineSearcherActivity::class.java))
            }
            R.id.home_top_view_textView_city -> {
                Logger.w("城市被点击了！")
                v.context.startActivity(Intent(v.context, CityPickerActivity::class.java))
            }
            R.id.daojishi -> {
                Logger.w("更多 被点击了！")
            }
            R.id.toNavi -> {
                Logger.i("导航")
                context.startActivity(Intent(context, NaviActivity::class.java))
            }
        }
    }

}