package com.itsukaa.realtime_bus.data.entity

class Line {


    var singleLine: SingleLine? = null
    var returnSingleLine: SingleLine? = null
    override fun toString(): String {
        return "Line(singleLine=$singleLine, returnSingleLine=$returnSingleLine)"
    }
}
