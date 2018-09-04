package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable

class Doctor: Parcelable {

    var name = "Alice Chang"
    var specialties = ArrayList<String>()
    var doctorCosts = ArrayList<DoctorCost>()
    var address = ""
    var phone = ""
    var prices = ""

    constructor() {

    }

    constructor(parcel: Parcel) {
        with(parcel, {
            name = readString() ?: ""
            readStringList(specialties) ?: ArrayList<String>()
            address = readString() ?: ""
            phone = readString() ?: ""
            prices = readString() ?: ""
            readTypedList(doctorCosts, DoctorCost.CREATOR)
        })
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        with(p0, {
            writeString(name)
            writeStringList(specialties)
            writeString(address)
            writeString(phone)
            writeString(prices)
            writeTypedList(doctorCosts)
        })
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR: Parcelable.Creator<Doctor> = object: Parcelable.Creator<Doctor> {
            override fun createFromParcel(p0: Parcel): Doctor {
                return Doctor(p0)
            }

            override fun newArray(p0: Int): Array<Doctor?> {
                return arrayOfNulls(p0)
            }
        }
    }

}