package com.itsukaa.realtime_bus.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itsukaa.realtime_bus.R
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.HotCity
import com.zaaach.citypicker.model.LocatedCity


class CityPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_picker)

        val hotCities: MutableList<HotCity> = ArrayList()
        hotCities.add(HotCity("北京", "北京", "101010100")) //code为城市代码
        hotCities.add(HotCity("上海", "上海", "101020100"))
        hotCities.add(HotCity("广州", "广东", "101280101"))
        hotCities.add(HotCity("深圳", "广东", "101280601"))
        hotCities.add(HotCity("杭州", "浙江", "101210101"))

        CityPicker.from(this)
            .enableAnimation(true)    //启用动画效果，默认无
            .setLocatedCity(LocatedCity("武汉", "湖北", "101210101"))
            .setHotCities(hotCities)
            .setOnPickListener(object : OnPickListener {
                override fun onPick(position: Int, data: City) {
                    Toast.makeText(applicationContext, data.name, Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(applicationContext, "取消选择", Toast.LENGTH_SHORT).show()
                }

                override fun onLocate() {

                }
            })
            .show()
    }
}