package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.vthai.sidecarprototype.activities.SplashActivity
import com.example.vthai.sidecarprototype.datasource.DataSourceManager
import com.example.vthai.sidecarprototype.datasource.DoctorOverviewAsyncTask
import com.example.vthai.sidecarprototype.model.Doctor

class SplashViewModel(app: Application): AndroidViewModel(app), DoctorOverviewAsyncTask.Listener {
    var doctor: MutableLiveData<Doctor>

    init {
        Log.d("SplashVM", "inside init")
        DataSourceManager.retrieveDoctorOverviewData(SplashActivity.TEST_DOC_ID, this)
        doctor = MutableLiveData()
        doctor.value = null
    }

    override fun onOverviewTaskCompleted(doctor: Doctor?) {
        this.doctor.value = doctor
    }
}