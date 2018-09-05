package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.net.Uri
import android.telephony.PhoneNumberUtils
import com.example.vthai.sidecarprototype.activities.DoctorActivity
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost

class DoctorViewModel(appContext: Application) : AndroidViewModel(appContext) {

    private lateinit var doctor: Doctor
    var selectedMapLiveData: MutableLiveData<HashMap<Int, Boolean>>
    var callFlag: MutableLiveData<Intent>
    var directionsFlag: MutableLiveData<Intent>
    var doctorTitleTextLiveData: MutableLiveData<String>
    var doctorSubTitleTextLiveData: MutableLiveData<String>
    var doctorCostListLiveData: MutableLiveData<List<DoctorCost>>
    var doctorAddressLiveData: MutableLiveData<String>
    var doctorPhoneLiveData: MutableLiveData<String>
    var doctorSpecialtiesLiveData: MutableLiveData<String>
    var doctorPricesLiveData: MutableLiveData<String>


    init {
        callFlag = MutableLiveData()
        directionsFlag = MutableLiveData()
        doctorTitleTextLiveData = MutableLiveData()
        doctorSubTitleTextLiveData = MutableLiveData()
        doctorCostListLiveData = MutableLiveData()
        doctorAddressLiveData = MutableLiveData()
        doctorPhoneLiveData = MutableLiveData()
        doctorSpecialtiesLiveData = MutableLiveData()
        doctorPricesLiveData = MutableLiveData()
        selectedMapLiveData = MutableLiveData()
    }

    //initialize all values
    fun notifyViewModelOnCreate(intent: Intent) {
        val doctor = intent.extras.getParcelable<Doctor>(DoctorActivity.DOCTOR_INTENT_KEY)
        if (doctor != null) this.doctor = doctor
        doctorTitleTextLiveData.value = doctor.name
        if (doctor.specialties.isNotEmpty()) doctorSubTitleTextLiveData.value = doctor.specialties[0]
        val sb = StringBuilder()
        for (specialty in doctor.specialties) {
            sb.appendln(specialty)
        }
        doctorSpecialtiesLiveData.value = sb.toString()
        doctorAddressLiveData.value = doctor.address
        doctorPhoneLiveData.value = PhoneNumberUtils.formatNumber(doctor.phone, "US")
        doctorPricesLiveData.value = doctor.prices
        doctorCostListLiveData.value = doctor.doctorCosts
    }

    fun notifyViewModelOnCallClicked() {
        val uriString = String.format("tel:%s", doctor.phone)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uriString)
        callFlag.value = intent
    }

    fun notifyViewModelOnDirectionsClicked() {
        val address = doctor.address.replace(' ', '+')
        directionsFlag.value = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$address"))
    }

}