package com.itsukaa.realtime_bus.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.*
import com.amap.api.services.busline.*
import com.amap.api.services.busline.BusLineQuery.SearchType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.itsukaa.realtime_bus.R
import com.itsukaa.realtime_bus.data.adapter.StopsAdapter
import com.itsukaa.realtime_bus.data.entity.SingleLine
import com.itsukaa.realtime_bus.data.entity.Station
import com.itsukaa.realtime_bus.server.data.getBusesByLineId
import com.orhanobut.logger.Logger
import java.util.*
import kotlin.collections.ArrayList


class LineDetailsActivity : AppCompatActivity(),
    AMapLocationListener {
    lateinit var terminalStation: String
    lateinit var singleLine: SingleLine
    lateinit var stationId: String
    lateinit var station: Station
    lateinit var aMap: AMap
    lateinit var aMapLocationClient: AMapLocationClient
    var flag = false
    var busMarkerList: MutableList<Marker> = emptyList<Marker>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_details)
        Logger.v("")


        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onCreate(savedInstanceState)
        aMap = mapView.map
        val myLocationStyle: MyLocationStyle =
            MyLocationStyle() //??????????????????????????????myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????
        myLocationStyle.interval(1000) //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
        aMap.myLocationStyle = myLocationStyle //?????????????????????Style
        aMap.uiSettings.isMyLocationButtonEnabled = true
        aMap.isMyLocationEnabled = true // ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
        singleLine =
            Gson().fromJson(intent.getStringExtra("singleLine"), SingleLine::class.java)
        station = Gson().fromJson(intent.getStringExtra("station"), Station::class.java)
        stationId = station.stationId.toString()
        terminalStation = singleLine.singleLineEndStationName.toString()

        Logger.d(singleLine)
        paintStations()
        paintLine()
        paintBuses()

        addTimerTask()
        val mRecyclerView = findViewById<RecyclerView>(R.id.stopsView)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = StopsAdapter(singleLine, stationId.toString())

        val aMapLocationClientOption = AMapLocationClientOption()
        aMapLocationClientOption.interval = 1000
        aMapLocationClient = AMapLocationClient(applicationContext)
        aMapLocationClient.setLocationListener(this)
        aMapLocationClient.setLocationOption(aMapLocationClientOption)
        aMapLocationClient.startLocation()

        val refresh = findViewById<FloatingActionButton>(R.id.refreshLine)
        refresh.setOnClickListener {
            paintBuses()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val mapView = findViewById<MapView>(R.id.busDetailsMap)
        mapView.onSaveInstanceState(outState)
    }

    private fun paintLine() {
        //??????
        val busLineQuery = BusLineQuery(singleLine.singleLineName, SearchType.BY_LINE_NAME, "??????")
        busLineQuery.pageSize = 10
        busLineQuery.pageNumber = 0
        val busLineSearch = BusLineSearch(this, busLineQuery)

        busLineSearch.setOnBusLineSearchListener { res: BusLineResult, i: Int ->
            for (busLine in res.busLines) {
                if (busLine.terminalStation.startsWith(terminalStation)) {
                    //???????????????????????????????????????
                    //????????????????????????
                    val coordinates = busLine.directionsCoordinates
                    val latLngs: MutableList<LatLng> = ArrayList()
                    val polylineOptions = PolylineOptions()
                    for (coordinate in coordinates) {
                        latLngs.add(LatLng(coordinate.latitude, coordinate.longitude))
                    }
                    polylineOptions.addAll(latLngs).color(Color.RED).width(10f)
                    aMap.addPolyline(polylineOptions)
                    aMap.reloadMap()
                    break
                }
            }
        }
        busLineSearch.searchBusLineAsyn()
    }


    private fun paintStations() {
        //???????????????
        val stopImg = bitmapDescriptorFromVector(this, R.drawable.station)
        for (station1 in singleLine.singleLineStations!!) {
            val stationName = station1.stationName
            val busStationQuery = BusStationQuery(stationName, "027")
            val busStationSearch = BusStationSearch(this, busStationQuery)
            busStationSearch.setOnBusStationSearchListener { result, i ->
                val busStations = result.busStations
                val busStation = busStations[0]
                val markerOptions = MarkerOptions().position(
                    LatLng(
                        busStation.latLonPoint.latitude,
                        busStation.latLonPoint.longitude
                    )
                ).icon(stopImg)
                    .visible(true)
                    .title(stationName)
                    .alpha(1f)
                val marker = aMap.addMarker(markerOptions)
            }
            busStationSearch.searchBusStationAsyn()
        }
    }


    private fun paintBuses() {
        for (marker in busMarkerList) {
            marker.isVisible = false
        }
        busMarkerList.clear()

        val busImg = bitmapDescriptorFromVector(this, R.drawable.small_bus)
        Thread {
            val buses = singleLine.singleLineId?.let { getBusesByLineId(it) }!!
            if (buses.isNotEmpty()) {
                for (bus in buses) {
                    val markerOptions = MarkerOptions().position(
                        LatLng(
                            bus.location!!.latitude.toDouble(),
                            bus.location.longitude.toDouble()
                        )
                    ).icon(busImg)
                        .visible(true)
                        .alpha(1f)
                    val marker = aMap.addMarker(markerOptions)
                    busMarkerList.add(marker)
                }
            }
        }.start()
    }


    private fun addTimerTask() {
        val timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                paintBuses()
            }
        }
        timer.schedule(timerTask, 0, 10000)
    }


    private fun scale2Zoom(scale: Float): Int {
        if (scale <= 10) return 19 else if (scale <= 25) return 18 else if (scale <= 50) return 17 else if (scale <= 100) return 16 else if (scale <= 200) return 15 else if (scale <= 500) return 14 else if (scale <= 1000) return 13 else if (scale <= 2000) return 12 else if (scale <= 5000) return 11 else if (scale <= 10000) return 10 else if (scale <= 20000) return 9 else if (scale <= 30000) return 8 else if (scale <= 50000) return 7 else if (scale <= 100000) return 6 else if (scale <= 200000) return 5 else if (scale <= 500000) return 4 else if (scale <= 1000000) return 3 else if (scale > 1000000) return 2
        return 20
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onLocationChanged(location: AMapLocation?) {
        if (!flag) {
            flag = true
            val mCameraUpdate = CameraUpdateFactory.newCameraPosition(
                CameraPosition(
                    LatLng(location!!.latitude, location.longitude), 15f, 0f, 0f
                )
            )
            aMap.moveCamera(mCameraUpdate)
        }
    }
}