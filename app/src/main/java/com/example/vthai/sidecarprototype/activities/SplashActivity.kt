package com.example.vthai.sidecarprototype.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.viewmodels.SplashViewModel

class SplashActivity: AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSplashViewModel()
    }

    private fun setupSplashViewModel() {
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SplashViewModel::class.java)
        viewModel.doctor.observe(this, doctorObserver)
    }

    private val doctorObserver = Observer<Doctor>() {
        if(it != null) startDoctorActivity(it)
    }

    private fun startDoctorActivity(doctor: Doctor) {
        val intent = Intent(this, DoctorActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DoctorActivity.DOCTOR_INTENT_KEY, doctor)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TEST_DOC_ID = "12345"
    }
}