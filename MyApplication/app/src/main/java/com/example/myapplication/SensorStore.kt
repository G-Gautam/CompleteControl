package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.CountDownTimer
import android.os.Handler
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import org.w3c.dom.Text
import java.util.*

public class SensorStore : SensorEventListener {
    private lateinit var mSensorManager: SensorManager
    private lateinit var con: Context
    private lateinit var xReadingText: TextView
    private lateinit var yReadingText: TextView
    private lateinit var zReadingText: TextView
    private var acc: Int = 0

    constructor(context: Context, xRead: TextView, yRead: TextView, zRead: TextView){
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        con = context
        xReadingText = xRead
        yReadingText = yRead
        zReadingText = zRead
//        val deviceSensors: List<Sensor> = mSensorManager.getSensorList(Sensor.TYPE_ALL)
//        Log.v("Total sensors",""+deviceSensors.size)
//        deviceSensors.forEach{
//            Log.v("Sensor name",""+it)
//        }
    }

    fun wristFlickDetected() {
        //Toast.makeText(con, "Starting", Toast.LENGTH_SHORT).show()
        var linearSensor : Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mSensorManager.registerListener(this, linearSensor, SensorManager.SENSOR_DELAY_GAME)
        Log.v("Status", "Registered Sensor and is actively listening")
        //startTimerToUnRegister()
    }

    private fun startTimerToUnRegister(){
        Handler().postDelayed({
            Log.v("Status", "Calling Destroy")
            destroy()
        }, 5000)
    }
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION && acc == 1)
                xReadingText.text = "X-Reading" + p0.values[0].toString().substring(0,5)
                yReadingText.text = "Y-Reading" + p0.values[1].toString().substring(0,5)
                zReadingText.text = "Z-Reading" + p0.values[2].toString().substring(0,5)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        if (sensor != null) {
            when (accuracy) {
                0 -> {
                    Log.v("Accuracy", "Low")
                    acc = 0
                }
                1 -> {
                    Log.v("Accuracy", "Med")
                    acc = 1
                }
                2 -> {
                    Log.v("Accuracy", "High")
                    acc = 1
                }
                3 -> {
                    Log.v("Accuracy", "Perfect")
                    acc = 1
                }
            }
        }
    }

    fun destroy() {
//        unregister the sensor onPause else it will be active even if the activity is closed
        Log.v("Status", "Destroyed all listeners to sensor")
        mSensorManager.unregisterListener(this)
    }
}