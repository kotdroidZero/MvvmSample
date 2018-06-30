package com.kotdroid.samplemvvm.views.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.kotdroid.samplemvvm.R
import com.kotdroid.samplemvvm.repository.models.User
import com.kotdroid.samplemvvm.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //viewModel for taking care of this View(MainActivity)
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.GONE


        //attaching this observable view to to the ViewModel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        observeData()

        btnProfile.setOnClickListener {
            //hit api and get profile
            mainViewModel.getUser()
        }

    }

    private fun observeData() {
        //keep observing the data so that if there is any change in data comes you (the observer)
        // will be notified , and accordingly you can do changes in your ui
        mainViewModel.user.observe(this, Observer {
            if (null != it) {
                updateUi(it)
            }
        })

        //observing when to show loader
        mainViewModel.isShowLoader.observe(this, Observer {
            if (null != it && it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(user: User) {
        ivUserProfile.setImageURI(user.data.avatar)
        tvName.text = "Welcome ${user.data.first_name}  your id is : ${user.data.id}"
    }
}
