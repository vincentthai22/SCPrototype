package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.view.View
import com.example.vthai.sidecarprototype.activities.DoctorActivity
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost

class DoctorViewModel(appContext: Application) : AndroidViewModel(appContext) {

    lateinit var doctor: Doctor
    var selectedMapLiveData: MutableLiveData<HashMap<Int, Boolean>>
    var callFlag: MutableLiveData<Intent>
    var directionsFlag: MutableLiveData<Intent>
    var doctorTitleTextLiveData: MutableLiveData<String>
    var doctorSubTitleTextLiveData: MutableLiveData<String>
    var doctorCostListLiveData: MutableLiveData<List<DoctorCost>>


    init {
        callFlag = MutableLiveData()
        directionsFlag = MutableLiveData()
        doctorTitleTextLiveData = MutableLiveData()
        doctorSubTitleTextLiveData = MutableLiveData()
        selectedMapLiveData = MutableLiveData()
        doctorCostListLiveData = MutableLiveData()
    }

    //initialize all values
    fun notifyViewModelOnCreate(intent: Intent) {
        doctor = intent.extras.getParcelable(DoctorActivity.DOCTOR_INTENT_KEY)
        doctorTitleTextLiveData.value = doctor.name
        if(doctor.specialties.isNotEmpty()) doctorSubTitleTextLiveData.value = doctor.specialties[0]
        doctorCostListLiveData.value = doctor.doctorCosts
    }

    fun onCallClicked(view: View) {
        val uriString = String.format("tel:%s", doctor.phone)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uriString)
        callFlag.value = intent
    }

    fun onDirectionsClicked(v: View) {
        val address = doctor.address.toString().replace(' ', '+')
        directionsFlag.value = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$address"))
    }

}