package com.example.myapplication
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MainActivity : WearableActivity(), SensorEventListener {

    private lateinit var xReadingText: TextView
    private lateinit var yReadingText: TextView
    private lateinit var zReadingText: TextView
    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION)
                xReadingText.text = "X-Reading" + p0.values[0].toString().substring(0,5)
                yReadingText.text = "Y-Reading" + p0.values[1].toString().substring(0,5)
                zReadingText.text = "Z-Reading" + p0.values[2].toString().substring(0,5)
                if(p0.values[1] > 15 || p0.values[1] < -15)
                    Toast.makeText(this, "Starting dummy activity", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        xReadingText = findViewById(R.id.xReadingText)
        yReadingText = findViewById(R.id.yReadingText)
        zReadingText = findViewById(R.id.zReadingText)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        Define the sensor
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        val deviceSensors: List<Sensor> = mSensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.v("Total sensors",""+deviceSensors.size)
        deviceSensors.forEach{
            Log.v("Sensor name",""+it)
        }
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
