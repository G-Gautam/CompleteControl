package com.example.myapplication

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class SensorBridge{
    private lateinit var database: DatabaseReference

    fun add(payload: Payload) : Boolean{
        database = FirebaseDatabase.getInstance().reference
        try{
            database.child("/").setValue(payload)
        }
        catch(ex : Exception){
            Log.v("Exception", ex.message)
            return false
        }
        return true
    }
}