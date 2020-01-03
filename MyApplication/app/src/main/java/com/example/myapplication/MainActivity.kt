package com.example.myapplication
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.widget.Button
import android.widget.TextView

class MainActivity : WearableActivity() {

    private lateinit var xReadingText: TextView
    private lateinit var yReadingText: TextView
    private lateinit var zReadingText: TextView
    private lateinit var sensorStore: SensorStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        xReadingText = findViewById(R.id.xReadingText)
        yReadingText = findViewById(R.id.yReadingText)
        zReadingText = findViewById(R.id.zReadingText)
        sensorStore = SensorStore(this, xReadingText, yReadingText, zReadingText)

        setAmbientEnabled()
    }
    
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    
    

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.v("Status", keyCode.toString())
        return when (keyCode) {
            KeyEvent.KEYCODE_NAVIGATE_NEXT ->
                // Flick wrist out
                triggerFlick()
            else -> {
                super.onKeyDown(keyCode, event)
            }
        }
    }

    private fun triggerFlick() : Boolean{
        sensorStore.wristFlickDetected()
        return false
    }
}
