package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

public class SensorStore : SensorEventListener {
    private lateinit var mSensorManager: SensorManager
    private lateinit var con: Context
    private lateinit var xReadingText: TextView
    private lateinit var yReadingText: TextView
    private lateinit var zReadingText: TextView

    constructor(context: Context){
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        con = context
    }

    fun startGyroReading(){
        var gyroSensor : Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if(gyroSensor != null){
            mSensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
            Log.v("Sensor", "Sensor Found and is not null")
        }else{
            Log.v("Sensor", "Sensor Not Found")
        }
    }
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_ROTATION_VECTOR)
                xReadingText.text = "X-Reading" + p0.values[0].toString().substring(0,5)
                yReadingText.text = "Y-Reading" + p0.values[1].toString().substring(0,5)
                zReadingText.text = "Z-Reading" + p0.values[2].toString().substring(0,5)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun destroy() {
//        unregister the sensor onPause else it will be active even if the activity is closed
        mSensorManager.unregisterListener(this)
    }
}