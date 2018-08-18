package com.kotdroid.samplemvvm.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kotdroid.samplemvvm.services.GeofenceTransitionsIntentService

class GeoFencingReceivers : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (null != context && null != intent)
            GeofenceTransitionsIntentService.enqueueWork(context, intent)
    }
}