package com.example.myapplication
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.Toast

class MainActivity : WearableActivity(), SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null

    override fun onSensorChanged(p0: SensorEvent?) {
//        Sensor change value
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_ACCELEROMETER)
                Toast.makeText(this, "WE GOT THIS BOIS", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        Define the sensor
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        setAmbientEnabled()
    }

    override fun onResume() {
        super.onResume()
//        Register the sensor on resume of the activity
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
//        unregister the sensor onPause else it will be active even if the activity is closed
        mSensorManager.unregisterListener(this)
    }
}
