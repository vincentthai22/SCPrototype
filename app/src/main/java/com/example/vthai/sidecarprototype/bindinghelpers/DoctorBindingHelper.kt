package com.example.vthai.sidecarprototype.bindinghelpers

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.telephony.PhoneNumberUtils
import android.widget.TextView
import com.example.vthai.sidecarprototype.adapters.DoctorCostsRecyclerAdapter
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost

class DoctorBindingHelper {

    companion object {

        @JvmStatic
        @BindingAdapter("formattedText")
        fun setFormattedText(v: TextView, doctor: Doctor) {
            v.text = StringBuilder().apply {
                for (specialty in doctor.specialties) {
                    appendln(specialty)
                }
            }.toString()
        }

        @JvmStatic
        @BindingAdapter("formattedPhoneText")
        fun setFormattedPhoneText(v: TextView, phone: String) {
            v.text = PhoneNumberUtils.formatNumber(phone, "US")
        }

        @JvmStatic
        @BindingAdapter("doctorCostList")
        fun setDoctorCostList(rv: RecyclerView, list: List<DoctorCost>?) {
            (rv.adapter as? DoctorCostsRecyclerAdapter)?.let {
                if(list != null) {
                    if (list is ArrayList) it.costList = list
                    else it.costList = ArrayList(list)
                }
            }
        }
    }


}