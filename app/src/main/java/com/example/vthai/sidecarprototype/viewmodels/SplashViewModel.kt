package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.vthai.sidecarprototype.activities.SplashActivity
import com.example.vthai.sidecarprototype.datasource.DataSourceManager
import com.example.vthai.sidecarprototype.datasource.DoctorCostsAsyncTask
import com.example.vthai.sidecarprototype.datasource.DoctorOverviewAsyncTask
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost

class SplashViewModel(app: Application): AndroidViewModel(app), DoctorOverviewAsyncTask.Listener, DoctorCostsAsyncTask.Listener {
    var doctor: MutableLiveData<Doctor>
    var doctorCosts: ArrayList<DoctorCost> = ArrayList()

    init {
        Log.d("SplashVM", "inside init")
        DataSourceManager.retrieveDoctorCostData(SplashActivity.TEST_DOC_ID, this)
        doctor = MutableLiveData()
        doctor.value = null
    }

    override fun onDoctorCostTaskCompleted(doctorCosts: ArrayList<DoctorCost>) {
        this.doctorCosts = doctorCosts
        DataSourceManager.retrieveDoctorOverviewData(SplashActivity.TEST_DOC_ID, this)
    }

    override fun onOverviewTaskCompleted(doctor: Doctor?) {
        doctor?.doctorCosts?.addAll(doctorCosts)
        this.doctor.value = doctor
    }
}