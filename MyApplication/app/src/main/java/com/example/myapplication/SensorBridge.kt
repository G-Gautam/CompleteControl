package com.example.myapplication

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
import java.time.LocalDateTime

class SensorBridge{
    private lateinit var database: DatabaseReference

    fun add(payload: Payload) : Boolean{
        database = FirebaseDatabase.getInstance().reference
        try {
            if (payload.hasValues()) {
                database.child("/").child(LocalDateTime.now().withNano(0).toString()).setValue(payload)
            }
            else{
                return false
            }
        }
        catch(ex : Exception){
            Log.v("Exception", ex.message)
            return false
        }
        return true
    }
}