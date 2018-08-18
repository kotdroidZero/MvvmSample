package com.kotdroid.samplemvvm.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.kotdroid.samplemvvm.R

class MarshMallowPermissions(private val activity: Activity) {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 99

    }

//    private var mActivity: Activity = mFragment.activity!!

    val isPermissionGrantedForLocation: Boolean
        get() = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermissionForLocation() {
        if (activity.shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
            showAlertDialog(activity.getString(R.string.location_permission_needed),
                    DialogInterface.OnClickListener { dialog, which ->
                        activity.requestPermissions(
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                LOCATION_PERMISSION_REQUEST_CODE)
                    }, null)
        } else {
            activity.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
        }
    }


    private fun showAlertDialog(message: String,
                                okListener: DialogInterface.OnClickListener,
                                cancelListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(activity.getString(R.string.ok), okListener)
                .setNegativeButton(activity.getString(R.string.cancel), cancelListener)
                .create()
                .show()
    }
}