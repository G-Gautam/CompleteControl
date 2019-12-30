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
    private var mSensorManager: SensorManager
    private var con: Context
    private var xReadingText: TextView
    private var yReadingText: TextView
    private var zReadingText: TextView
    private var xSlope: Float = 0.0F
    private var ySlope: Float = 0.0F
    private var zSlope: Float = 0.0F
    private var acc: Int = 0
    private lateinit var xList: MutableList<Float>
    private lateinit var yList: MutableList<Float>
    private lateinit var zList: MutableList<Float>

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
        initializeLists()
        var linearSensor : Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mSensorManager.registerListener(this, linearSensor, SensorManager.SENSOR_DELAY_GAME)
        startTimerToUnRegister()
    }

    private fun initializeLists(){
        xList = mutableListOf()
        yList = mutableListOf()
        zList = mutableListOf()
    }

    private fun startTimerToUnRegister(){
        Handler().postDelayed({
            calculationHelper()
            destroy()
        }, 1000)
    }

    private fun calculationHelper(){
        //find trend line by using least square method
        val timeSlope = 501
        var xMean = 0.0F
        var yMean = 0.0F
        var zMean = 0.0F

        xSlope = 0.0F
        ySlope = 0.0F
        zSlope = 0.0F

        for(i in 0 until xList.size){
            xMean += xList[i]
            yMean += yList[i]
            zMean += zList[i]
        }

        xMean /= xList.size
        yMean /= yList.size
        zMean /= zList.size

        for(i in 0 until xList.size){
            xSlope += ((xList[i]-xMean)*(1000/xList.size*i-timeSlope))/((1000/xList.size*i-timeSlope)*(1000/xList.size*i-timeSlope))
            ySlope += ((yList[i]-yMean)*(1000/yList.size*i-timeSlope))/((1000/yList.size*i-timeSlope)*(1000/yList.size*i-timeSlope))
            zSlope += ((zList[i]-zMean)*(1000/zList.size*i-timeSlope))/((1000/zList.size*i-timeSlope)*(1000/zList.size*i-timeSlope))
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION && acc == 1)
                xReadingText.text = "X-Reading" + p0.values[0].toString().substring(0,3)
                yReadingText.text = "Y-Reading" + p0.values[1].toString().substring(0,3)
                zReadingText.text = "Z-Reading" + p0.values[2].toString().substring(0,3)
                xList.add(p0.values[0])
                yList.add(p0.values[1])
                zList.add(p0.values[2])
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
        mSensorManager.unregisterListener(this)

        var payload = Payload(xSlope, ySlope, zSlope)
        var result = addToFirebase(payload)
        if(result){
            xReadingText.text = xSlope.toString()
            yReadingText.text = ySlope.toString()
            zReadingText.text = zSlope.toString()
        }
        else{
            xReadingText.text = "Error"
            yReadingText.text = "Error"
            zReadingText.text = "Error"
        }
    }

    private fun addToFirebase(payload: Payload) : Boolean{
        val sensorBridge = SensorBridge()
        return sensorBridge.add(payload)
    }
}