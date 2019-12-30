package com.example.myapplication

class Payload(private var xTrend: Float, private var yTrend: Float, private var zTrend: Float) {

    fun XTrend() : Float{
        return this.xTrend
    }

    fun YTrend() : Float{
        return this.yTrend
    }

    fun ZTrend() : Float{
        return this.zTrend
    }

    fun hasValues() : Boolean{
        if(xTrend != null && yTrend != null && zTrend != null){
            return true
        }
        return false
    }
}

