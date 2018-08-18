package com.kotdroid.samplemvvm.views.activities

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.kotdroid.samplemvvm.R
import com.kotdroid.samplemvvm.services.GeofenceTransitionsIntentService
import com.kotdroid.samplemvvm.utils.MarshMallowPermissions
import com.kotdroid.samplemvvm.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //viewModel for taking care of this View(MainActivity)
    private lateinit var mainViewModel: MainViewModel


    lateinit var geoFencingClient: GeofencingClient
    private var geofencList = mutableListOf<Geofence>()
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private lateinit var mMarshmallowPermisssion: MarshMallowPermissions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        //initializing MarshmallowPermission class
        mMarshmallowPermisssion = MarshMallowPermissions(this)

        //initializing geoFenceClient
        geoFencingClient = LocationServices.getGeofencingClient(this)

        //adding geoFence to the geoFenceList
        geofencList.add(Geofence.Builder()
                .setRequestId("TestGeoFence")
                .setCircularRegion(30.710937, 76.6842653, 3f)
                .setExpirationDuration(50000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT or Geofence.GEOFENCE_TRANSITION_ENTER)
                .build())

        //tell the geoFencingClient about GeoFencingRequest and PendingIntent
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            geoFencingClient.addGeofences(geoFencingRequest(), geofencePendingIntent).run {
                addOnSuccessListener {
                    Log.e("Geo", "Success")
                }

                addOnFailureListener {
                    Log.e("Geo", "Failed")

                }

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mMarshmallowPermisssion.requestPermissionForLocation()
            }
        }



        progressBar.visibility = View.GONE
//
//
//        //attaching this observable view to to the ViewModel
//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//
//        observeData()
//
//        btnProfile.setOnClickListener {
//            //hit api and get profile
//            mainViewModel.getUser()
//        }


    }


    private fun geoFencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder()
                .apply {
                    setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    addGeofences(geofencList)
                }
                .build()
    }
//    private fun observeData() {
//        //keep observing the data so that if there is any change in data comes you (the observer)
//        // will be notified , and accordingly you can do changes in your ui
//        mainViewModel.user.observe(this, Observer {
//            if (null != it) {
//                updateUi(it)
//            }
//        })
//
//        //observing when to show loader
//        mainViewModel.isShowLoader.observe(this, Observer {
//            if (null != it && it) {
//                progressBar.visibility = View.VISIBLE
//            } else {
//                progressBar.visibility = View.GONE
//            }
//        })
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateUi(user: User) {
//        ivUserProfile.setImageURI(user.data.avatar)
//        tvName.text = "Welcome ${user.data.first_name}  your id is : ${user.data.id}"
//    }
}
