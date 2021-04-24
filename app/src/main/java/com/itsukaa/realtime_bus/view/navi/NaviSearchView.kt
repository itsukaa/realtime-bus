package com.itsukaa.realtime_bus.view.navi

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.itsukaa.realtime_bus.R

class NaviSearchView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_navi_search, this)
    }
}