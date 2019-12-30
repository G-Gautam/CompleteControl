package com.example.myapplication

import java.time.LocalDateTime

class Payload(var xTrend: Float, var yTrend: Float, var zTrend: Float) {
    fun hasValues() : Boolean{
        if(xTrend != null && yTrend != null && zTrend != null){
            return true
        }
        return false
    }
}

