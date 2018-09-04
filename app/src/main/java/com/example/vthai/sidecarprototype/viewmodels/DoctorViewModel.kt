package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import com.example.vthai.sidecarprototype.activities.DoctorActivity
import com.example.vthai.sidecarprototype.model.Doctor

class DoctorViewModel(appContext: Application) : AndroidViewModel(appContext) {

    var doctor: MutableLiveData<Doctor>

    init {
        doctor = MutableLiveData()
    }

    fun notifyViewModelOnCreate(intent: Intent) {
        val doctor = intent.extras.getParcelable<Doctor>(DoctorActivity.DOCTOR_INTENT_KEY)
        if(doctor != null) this.doctor.value = doctor
    }

}